$(document).ready(function() {
    $("#nav-login").addClass("active");
    setupMenu();
    $("#appVersion").text(loggedInUserInfo.adorationApplicationVersion);
});
