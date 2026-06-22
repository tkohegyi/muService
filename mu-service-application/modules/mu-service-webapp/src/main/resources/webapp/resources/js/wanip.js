var hoursToShow = 720;
var refreshTimer = null;

$(document).ready(function() {
    $("#nav-list").addClass("active");
    jQuery.ajaxSetup({async:false});
    setupMenu();
    jQuery.ajaxSetup({async:true});
    initBaseDay();
    getWanIpData();
    updateAutoRefresh();
});

function todayString() {
    var d = new Date();
    var mm = String(d.getMonth() + 1).padStart(2, '0');
    var dd = String(d.getDate()).padStart(2, '0');
    return d.getFullYear() + '-' + mm + '-' + dd;
}

function initBaseDay() {
    document.getElementById('baseDay').value = todayString();
}

function isBaseDayToday() {
    return document.getElementById('baseDay').value === todayString();
}

function getToMs() {
    if (isBaseDayToday()) {
        return Date.now();
    }
    var dayStr = document.getElementById('baseDay').value;
    return new Date(dayStr + 'T23:59:59.999').getTime();
}

function updateAutoRefresh() {
    if (refreshTimer !== null) {
        clearInterval(refreshTimer);
        refreshTimer = null;
    }
    if (hoursToShow === 3 && isBaseDayToday()) {
        refreshTimer = setInterval(getWanIpData, 60000);
    }
}

function setHours(n) {
    hoursToShow = n;
    updateAutoRefresh();
    getWanIpData();
}

function onBaseDayChange() {
    updateAutoRefresh();
    getWanIpData();
}

function getWanIpData() {
    var id = $("#wanIpId").attr('value');
    var toMs = getToMs();
    $.get('/appSecure/getWanIpData/' + id + '/' + hoursToShow + '/' + toMs, function(data) {
        var information = data.status;
        if (typeof information == "undefined" || information == null) {
            window.location.pathname = "/";
            return;
        }
        $('#headDescription').text("Description: " + information.description);
        $('#headType').text("Type: " + information.type);
        showWanIpTimeline(information, toMs);
    });
}

function showWanIpTimeline(json, toMs) {
    var points = json.points;
    if (!points || points.length === 0) {
        d3.select('#wanIpTimeline').selectAll('*').remove();
        d3.select('#wanIpTimeline').append("text").attr("x", 10).attr("y", 30)
          .text("No data available for the last " + hoursToShow + " hours.");
        return;
    }

    var rangeEnd = toMs;
    var rangeStart = rangeEnd - (hoursToShow * 60 * 60 * 1000);

    // Build ON intervals: each OK report marks ±1 minute as ON
    var HALF_WINDOW = 60 * 1000;
    var onIntervals = [];
    for (var i = 0; i < points.length; i++) {
        if (points[i].status === "OK") {
            onIntervals.push({ start: points[i].order - HALF_WINDOW, end: points[i].order + HALF_WINDOW });
        }
    }

    // Merge overlapping ON intervals
    onIntervals.sort(function(a, b) { return a.start - b.start; });
    var merged = [];
    for (var j = 0; j < onIntervals.length; j++) {
        if (merged.length === 0 || onIntervals[j].start > merged[merged.length - 1].end) {
            merged.push({ start: onIntervals[j].start, end: onIntervals[j].end });
        } else {
            merged[merged.length - 1].end = Math.max(merged[merged.length - 1].end, onIntervals[j].end);
        }
    }

    // D3 chart
    const width = 928;
    const height = 200;
    const marginTop = 20;
    const marginRight = 30;
    const marginBottom = 30;
    const marginLeft = 50;

    const x = d3.scaleTime([rangeStart, rangeEnd], [marginLeft, width - marginRight]);
    const y = d3.scaleLinear([0, 1], [height - marginBottom, marginTop]);

    var svg = d3.select('#wanIpTimeline')
        .attr("width", width)
        .attr("height", height)
        .attr("viewBox", [0, 0, width, height])
        .attr("style", "max-width: 100%; height: auto;");

    svg.selectAll('*').remove();

    // NOTOK background
    svg.append("rect")
        .attr("x", marginLeft)
        .attr("y", y(0.1))
        .attr("width", width - marginLeft - marginRight)
        .attr("height", y(0) - y(0.1))
        .attr("fill", "#ffcccc");

    // ON segments
    for (var m = 0; m < merged.length; m++) {
        var s = merged[m];
        var x1 = x(Math.max(s.start, rangeStart));
        var x2 = x(Math.min(s.end, rangeEnd));
        if (x2 > x1) {
            svg.append("rect")
                .attr("x", x1)
                .attr("y", y(1))
                .attr("width", x2 - x1)
                .attr("height", y(0) - y(1))
                .attr("fill", "#66bb6a");
        }
    }

    // X axis
    svg.append("g")
        .attr("transform", "translate(0," + (height - marginBottom) + ")")
        .call(d3.axisBottom(x).ticks(width / 100).tickSizeOuter(0).tickFormat(function(d) {
            return d3.timeDay(d) < d ? d3.timeFormat("%H:%M")(d) : d3.timeFormat("%d %b")(d);
        }));

    // Y axis with ON / NOTOK labels
    svg.append("g")
        .attr("transform", "translate(" + marginLeft + ",0)")
        .call(d3.axisLeft(y).tickValues([0, 1]).tickFormat(function(d) { return d === 1 ? "ON" : "NOTOK"; }))
        .call(function(g) { g.select(".domain").remove(); });
}
