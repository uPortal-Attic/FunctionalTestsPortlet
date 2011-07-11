<%@ include file="/WEB-INF/jsp/include.jsp" %>
<portlet:defineObjects/>
<h3>Portlet 2.0 CacheControl Test (PLT.22.2)</h3>
<div>
<p>This content was rendered by REMOTE_USER=${lastRenderUser} at <fmt:formatDate value="${lastRenderTime}" type="both" timeStyle="long" dateStyle="long"/>.</p>
<c:choose>
<c:when test="${empty renderRequest.ETag }">
<p>ETag not found on the request.</p>
</c:when>
<c:otherwise>
<p>ETag is '${renderRequest.ETag}'.</p>
</c:otherwise>
</c:choose>
<portlet:actionURL name="addMessage" var="action"></portlet:actionURL>
<form action="${action}">
Enter a message:&nbsp;<input name="message" type="text"/>&nbsp;<input type="submit" value="Submit"/>
</form>
<hr/>
<div>
<c:choose>
<c:when test="${empty messages}">
<p><i>No messages.</i></p>
</c:when>
<c:otherwise>
<ul>
<c:forEach items="${messages}" var="msg">
<li>${msg}</li>
</c:forEach>
</ul>
</c:otherwise>
</c:choose>
</div>
<hr/>
<div class="breadcrumb">
<a class="breadcrumb-1" href="<portlet:renderURL/>">Refresh</a>
</div>
</div>