<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title><tiles:insertAttribute name="pageTitle" ignore="true" /></title>
        <link type="text/css" rel="stylesheet" href="<c:url value='/css/custom-theme/jquery-ui-1.8.17.custom.css' />" />
        <link type="text/css" rel="stylesheet" href="<c:url value='/css/devoxxfr.css' />" />   
        <script type="text/javascript" src="<c:url value='/js/jquery-1.7.1.min.js' />"></script>
        <script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.17.custom.min.js' />"></script>
        <script type="text/javascript">
		$(function() {
			$(".ui-btn").button();
			
		    $(".jtable th").each(function() {
		        $(this).addClass("ui-state-default");
		    });
		    $(".jtable td").each(function() {
		        $(this).addClass("ui-widget-content");
		    });
		    $(".jtable tr").hover(function() {
		    	$(this).children("td").addClass("ui-state-hover");
		    }, function() {
		        $(this).children("td").removeClass("ui-state-hover");
		    });
		    $(".jtable tr").click(function() {		
		        $(this).children("td").toggleClass("ui-state-highlight");
		    });		
		});
		</script>
	</head>
	<body>
		<div>
			<tiles:insertAttribute name="header" />
						
			<div class="contentContainer">
			    <tiles:insertAttribute name="content" />
			</div>
			
			<tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>