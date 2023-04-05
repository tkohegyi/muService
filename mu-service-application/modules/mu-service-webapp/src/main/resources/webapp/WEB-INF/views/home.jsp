﻿<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<meta name="Description" content="muService - Security System" />
<meta name="Keywords" content="home,security" />
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
<link id="favicon" rel="shortcut icon" type="image/png" href="/resources/img/favicon.png">
</head>
<body class="body">
  <div class="container">
    <%@include file="../include/navbar.html" %>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <br />
    <hr />
    <style type="text/css">
        #appVersion {display:inline-block;}
        #sslSign {display:inline-block;}
    </style>
    <div id="appVersion"></div>
    <div id="sslSign" class="right">
        <script type="text/javascript"> //<![CDATA[
          var tlJsHost = ((window.location.protocol == "https:") ? "https://secure.trust-provider.com/" : "http://www.trustlogo.com/");
          document.write(unescape("%3Cscript src='" + tlJsHost + "trustlogo/javascript/trustlogo.js' type='text/javascript'%3E%3C/script%3E"));
        //]]></script>
        <script language="JavaScript" type="text/javascript">
          TrustLogo("https://www.positivessl.com/images/seals/positivessl_trust_seal_sm_124x32.png", "POSDV", "none");
        </script>
    </div>
  </div>

  <%@include file="../include/commonAlert.html" %>
  <%@include file="../include/commonConfirm.html" %>

</body>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/home.js"></script>
</html>
