<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ include file="/WEB-INF/jsp/testSelectorHeader.jsp" %>

<portlet:actionURL var="fileUploadActionUrl" name="fileUploadAction" />
<portlet:renderURL var="refreshUrl" />

<c:if test="${not empty fileInfo}">
    <ul>
        <c:forEach var="entry" items="${fileInfo}">
            <li>${entry.key} = ${entry.value}</li>
        </c:forEach>
    </ul>
</c:if>

<form action="${fileUploadActionUrl}" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="submit" name="Upload"/>
</form>

<a href="${refreshUrl}">Refresh</a>
