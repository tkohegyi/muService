$(document).ready(function() {
    $("#nav-list").addClass("active");
    jQuery.ajaxSetup({async:false});
    setupMenu();
    getGraph();
    jQuery.ajaxSetup({async:true});
});

function getGraph() {
    var id = $("#graphId").attr('value');
    $.get('/appSecure/getGraphSub/' + id, function(data) {
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

    var lineData = json;

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
  const y = d3.scaleLinear([0, d3.max(lineData, d => d.dValue)], [height - marginBottom, marginTop]);

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