<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url value="/index" var="indexUrl" />
<c:url value="/category" var="categoryUrl" />
<c:url value="/search" var="searchUrl" />
<c:url value="/login" var="loginUrl" />
<c:url value="/admin/index" var="adminUrl" />
<c:url value="/logout" var="logoutUrl" />
<div class="header">
	<div id="menuho">
		<ul>
			<li><a href="${indexUrl}" title="Accueil">Accueil</a></li>
			<li><a href="${categoryUrl}/5" title="Nouveaut&eacute;s">Nouveaut&eacute;s</a></li>
			<li><a href="${categoryUrl}/1" title="Informatique">Informatique</a></li>
			<li><a href="${categoryUrl}/4" title="M&eacute;decine">M&eacute;decine</a></li>
			<li><a href="${categoryUrl}/6" title="Droit">Droit</a></li>
			<li><a href="${categoryUrl}/3" title="Histoire">Histoire</a></li>      
			<li><a href="${categoryUrl}/2" title="Romans">Romans</a></li>
		</ul>
	</div>
</div>