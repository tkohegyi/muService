$(document).ready(function() {
    $("#nav-home").addClass("active");
    setupMenu();
    $("#appVersion").text(loggedInUserInfo.applicationVersion);
});
