<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
	$(function() {
		$("#menuTabs").tabs({
			ajaxOptions : {
				error : function(xhr, status, index, anchor) {
					$(anchor.hash).html("Couldn't load this tab.");
				}
			}
		});
	});
</script>
<div id="menuTabs" style="height: 400px;">
    <ul>
        <li><a href="#tabs-1">First</a></li>
        <li><a href="#tabs-2">Second</a></li>
        <li><a href="#tabs-3">Third</a></li>
        <li><a href='<c:url value="/admin/index" />'>Admin Index</a></li>
    </ul>
    <div id="tabs-1">Tab 1</div>
    <div id="tabs-2">Tab 2</div>
    <div id="tabs-3">Tab 3</div>
</div>