<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<spring:url var="urlUsuario" value="/usuario"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>


<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Busque um Lugar</title>
<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,800,700&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
<!-- Bootstrap -->
<%-- <link href="${context}/css/bootstrap.min.css" rel="stylesheet"> --%>
<link href="${context}/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"  href="${context}/css/smart-forms.css">
<link href="${context}/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${context}/js/jquery-ui.js"></script>
<script type="text/javascript" src="${context}/js/additional-methods.js"></script>

<!--[if lte IE 9]>   
  <script type="text/javascript" src="js/jquery.placeholder.min.js"></script>
<![endif]-->

<!--[if lte IE 8]>
  <link type="text/css" rel="stylesheet" href="css/smart-forms-ie8.css">
<![endif]-->

<script type="text/javascript">
  $(function() {
    $( ".menu-top .menu-li, .menu-user" ).click(function() {
    $(".menu-top .menu-li").removeClass('active');
	$(".menu-user").removeClass('active');
    $(this).addClass('active');
	$('body, html').css('overflow','hidden');
  });
  $( ".menus-top" ).hover(
    function() {$(".menu-top .menu-li, .menu-user");},
    function() {
		$(".menu-top .menu-li, .menu-user").removeClass('active');
		$('body, html').css('overflow','auto');
	}
  );
    $( ".menus-top" ).hover(
    function() {$(".menu-user");},
    function() {$(".menu-user").removeClass('active');}
  );
  });
</script>
<!-- SHADOWBOX -->
<link rel="stylesheet" type="text/css" href="${context}/includes/shadowbox/shadowbox.css">
<script type="text/javascript" src="${context}/includes/shadowbox/shadowbox.js"></script>
<script type="text/javascript">
Shadowbox.init();
</script>

</head>
