<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=3.0, user-scalable=yes" />
<meta name="HandheldFriendly" content="true" />
<meta name="apple-mobile-web-app-capable" content="YES" />
<meta name="author" content="Tamas Kohegyi" />
<meta name="Description" content="muService - Security System - Graph" />
<meta name="Keywords" content="home,security" />
<script src="/resources/js/external/jquery-3.6.4.min.js"></script>
<script src="/resources/js/external/bootstrap.min.js"></script>
<script src="/resources/js/external/d3.v7.min.js"></script>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/graph.js"></script>
<title>muService - Home Security System - Graph</title>
<link href="/resources/css/external/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/resources/css/menu.css" rel="stylesheet" media="screen">
<link id="favicon" rel="shortcut icon" type="image/png" href="/resources/img/favicon.png" />
</head>
<body class="body">
<div class="container">
    <%@include file="../include/navbar.html" %>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<div class="centerwidediv centerDiv">
	    <br/>
        <legend class="message-legend h4"></legend>
        <hidden id="graphId" value="${headId}" />
        <div id="headId">${headId}</div><div id="headInformation"></div>
        <div id="headDataSize"></div>
        <div id="headDataVisible"></div>
        <div id="headDataPosition"></div>
        <div id="headMinDate"></div>
        <div id="headMaxDate"></div>
        <br />
        <svg id="statisticInfo" width="1000" height="600"></svg>
	</div>

</body>
</html>
