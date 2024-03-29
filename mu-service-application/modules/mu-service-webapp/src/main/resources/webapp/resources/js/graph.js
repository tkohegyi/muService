$(document).ready(function() {
    $("#nav-list").addClass("active");
    jQuery.ajaxSetup({async:false});
    setupMenu();
    getGraph();
    jQuery.ajaxSetup({async:true});
});

var dataVisibleSize = null;
var dataStartingPosition = null;
var dataSize = null;
var headId = null;

function getGraph() {
    var id = $("#graphId").attr('value');
    headId = id;
    var request = '/appSecure/getGraphSub/' + id;
    if (typeof dataSize != "undefined" && dataSize != null) {
        request += '/' + dataStartingPosition + '/' + dataVisibleSize;
    }
    $.get(request, function(data) {
        var information = data;
        if (typeof information == "undefined" || information == null || information.error != null) {
            //something was wrong with either the server or with the request, let's go back
            window.location.pathname = "/";
            return;
        }
        showGraph(information.status);
    });
}

function showGraph(json) {
    // using constants for width and height
    var CHART_WIDTH = 730+60;
    var CHART_HEIGHT = 300;

    var lineData = json.seriesA;
    $('#headInformation').text(json.headInformation);
    $('#headDataSize').text("Total data rows: " + json.dataSize);
    $('#headDataVisible').text("Visible data rows: " + json.dataVisibleSize);
    $('#headDataPosition').text("Starting data row: " + json.dataStartingPosition);
    $('#headMinDate').text("Start date of graph: " + json.dateMin);
    $('#headMaxDate').text("End date of graph: " + json.dateMax);
    dataVisibleSize = Number(json.dataVisibleSize);
    dataStartingPosition = Number(json.dataStartingPosition);
    dataSize = Number(json.dataSize);

    // Show svg
    $('#statisticInfo').css('display', 'block');
    var statisticSvg = d3.select('#statisticInfo');
    // for redraw
    statisticSvg.selectAll('*').remove();

// Declare the chart dimensions and margins.
  const width = 928;
  const height = 500;
  const marginTop = 20;
  const marginRight = 30;
  const marginBottom = 30;
  const marginLeft = 40;

  // Declare the x (horizontal position) scale.
  const x = d3.scaleUtc(d3.extent(lineData, d => d.order), [marginLeft, width - marginRight]);
  // Declare the y (vertical position) scale.

  var min = d3.min(lineData, d => d.dValue);
  if (min > 0) { min = 0; }
  const y = d3.scaleLinear([min, d3.max(lineData, d => d.dValue)], [height - marginBottom, marginTop]);

  // Declare the line generator.
  const line = d3.line()
        .x(d => x(d.order))
        .y(d => y(d.dValue));

  // Create the SVG container.
  var svg = d3.select('#statisticInfo')
      .attr("width", width)
      .attr("height", height)
      .attr("viewBox", [0, 0, width, height])
      .attr("style", "max-width: 100%; height: auto; height: intrinsic;");

  // Add the x-axis.
  svg.append("g")
      .attr("transform", `translate(0,${height - marginBottom})`)
      .call(d3.axisBottom(x).ticks(width / 80).tickSizeOuter(0));

  // Add the y-axis, remove the domain line, add grid lines and a label.
  svg.append("g")
      .attr("transform", `translate(${marginLeft},0)`)
      .call(d3.axisLeft(y).ticks(height / 40))
      .call(g => g.select(".domain").remove())
      .call(g => g.selectAll(".tick line").clone()
          .attr("x2", width - marginLeft - marginRight)
          .attr("stroke-opacity", 0.1))
      .call(g => g.append("text")
          .attr("x", -marginLeft)
          .attr("y", 10)
          .attr("fill", "currentColor")
          .attr("text-anchor", "start")
          .text("Temperature"));

  // Append a path for the line.
  svg.append("path")
      .attr("fill", "none")
      .attr("stroke", "steelblue")
      .attr("stroke-width", 1.5)
      .attr("d", line(lineData));
}

function moreDataButton() {
    dataVisibleSize *= 2;
    getGraph();
}

function lessDataButton() {
    dataVisibleSize /= 2;
    dataVisibleSize = Math.round(dataVisibleSize);
    getGraph();
}

function forwardButton() {
    dataStartingPosition += dataVisibleSize / 2;
    dataStartingPosition = Math.round(dataStartingPosition);
    getGraph();
}

function backwardButton() {
    dataStartingPosition -= dataVisibleSize / 2;
    dataStartingPosition = Math.round(dataStartingPosition);
    getGraph();
}