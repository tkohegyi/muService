<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=3.0, user-scalable=yes" />
<meta name="HandheldFriendly" content="true" />
<meta name="apple-mobile-web-app-capable" content="YES" />
<meta name="author" content="Tamas Kohegyi" />
<meta name="Description" content="muService - Security System" />
<meta name="Keywords" content="home security" />
<meta property="og:image" content="https://muservice.magyar.website/resources/img/topimage3.jpg"/>
<meta property="og:url" content="https://muservice.magyar.website/"/>
<meta property="og:type" content="website"/>
<meta property="og:title" content="muService - Security System"/>
<meta property="og:description" content="muService - Security System"/>
<script src="/resources/js/external/jquery-3.6.4.min.js"></script>
<script src="/resources/js/external/bootstrap.min.js"></script>
<title>muService - Home Security System</title>
<link href="/resources/css/external/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/resources/css/menu.css" rel="stylesheet" media="screen">
<link id="favicon" rel="shortcut icon" type="image/png" href="/resources/img/favicon.png" />
</head>
<body class="body">
  <div class="container">
    <%@include file="../include/navbar.html" %>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <div class="centerwidediv textWebkitCenter">
		<br />
		Oops, a mistake has been made.
		<br />
    </div>
    <div class="centerwidediv">We have received a request on this website which unfortunately cannot be fulfilled.
    The site operators have received a reminder of this error and are working to ensure that this does not happen again.
    In the meantime, please be patient as it may take several days to correct the error.
		<br/>
    <hr />
		<br/>
		<div class="textWebkitCenter">Please select an item from the menu above, or wait 10 seconds and the page will reload with the home page.</div>
		<br />
	</div>
  </div>
  <script src="/resources/js/common.js"></script>
  <script src="/resources/js/E404.js"></script>
</body>
</html>
