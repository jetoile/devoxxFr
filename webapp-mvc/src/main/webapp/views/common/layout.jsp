<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/soat.css' />" />
		<title><tiles:insertAttribute name="pageTitle" ignore="true" /></title>
	</head>
	<body>
		<div class="homeContainer">
			<tiles:insertAttribute name="header" />
			
			<div class="colContainer">
			    <tiles:insertAttribute name="content" />
			</div>
			
			<tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>