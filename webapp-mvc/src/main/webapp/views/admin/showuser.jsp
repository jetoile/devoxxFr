<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty userResponse}">
<table>
    <tr>
        <td>
            <div><img style="width: 150px" src="http://www.gravatar.com/avatar/?s=150" alt="gravatar" /></div>
            <div>Name : <c:out value="${userResponse.name}" /></div>
        </td>
<%--         <td class="description">
            <div class="bookTitle"><c:out value="${book.title}" /></div>
            <div class="bookAuthor"><c:out value="${book.author}" /></div>
            <div><c:out value="${book.longDescription}" /></div>
            <div align="right"><input type="button" value="Reserver" /></div>
        </td> --%>
    </tr>
</table>
</c:if>