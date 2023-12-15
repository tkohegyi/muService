$(document).ready(function() {
    $("#nav-list").addClass("active");
    jQuery.ajaxSetup({async:false});
    setupMenu();
    jQuery.ajaxSetup({async:true});
    getList();
});

function getList() {
    $("#list").hide();
    $.get('/appSecure/getList', function(data) {
        var information = data.data;
        if (typeof information == "undefined" || information == null || information.error != null) {
            //something was wrong with either the server or with the request, let's go back
            window.location.pathname = "/";
            return;
        }
        //we have something to show
        var r = "<table><tbody><tr><td>HeadId</td><td>Description</td><td>Type</td><td>Last Information</td><td>Last Information Date</td><td/></tr>";
        for (var i = 0; i < information.length; i++) {
            item = information[i];
            r += "<tr><td>" + item.id + "</td><td>" + item.description + "</td><td>"
                + item.type + "</td><td>" + item.lastInformation + "</td><td>" + item.lastInformationDate + "</td>";
            if (item.drawable) {
                r += "<td><a id=\"graph-button" + item.id + "\" class=\"btn btn-primary\" href=\"/appSecure/getGraph/" + item.id + "\">Show</a></td>";
            } else {
                r += "<td/>";
            }
            r += "</tr>";
        }
        r += "</tbody></table>";
        var list = $("<div id=\"list\">" + r + "</div>");
        $("#list").replaceWith(list);
        $("#list").show();
    });
}
