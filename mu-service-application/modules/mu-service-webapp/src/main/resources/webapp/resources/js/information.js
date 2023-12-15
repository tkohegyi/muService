$(document).ready(function() {
    $("#nav-information").addClass("active");
    jQuery.ajaxSetup({async:false});
    setupMenu();
    jQuery.ajaxSetup({async:true});
    getInformation();
});

function getInformation() {
    $("#group").hide();
    $("#dataSource").hide();
    $("#graph").hide();
    $.get('/appSecure/getInformation', function(data) {
        var information = data;
        if (typeof information == "undefined" || information == null || information.error != null) {
            //something was wrong with either the server or with the request, let's go back
            window.location.pathname = "/";
            return;
        }
        //we have something to show
        //TODO
    });
}
