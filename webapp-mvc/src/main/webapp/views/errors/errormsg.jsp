<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty error}">
	<c:url value="/" var="indexUrl" />
	<script type="text/javascript">
	$(function() {
		$("#dialogErr").dialog({
			title: '<spring:message code="admin.error.dialog.title" text="Error" />',
			width: 600,
	        autoOpen: true,
	        modal: true,
	        position: ['center','middle'],
	        beforeClose: function(event, ui) { location.href = '${indexUrl}'; }
		});
	});
	</script>	
	<div id="dialogErr">
        <div style="padding: 0 .7em;" class="ui-state-error ui-corner-all"> 
            <p><span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-alert"></span> 
            <spring:message code="${error}" arguments="${errorParams}" argumentSeparator="§" text="No message for this locale" /></p>
        </div>
    </div>		
</c:if>