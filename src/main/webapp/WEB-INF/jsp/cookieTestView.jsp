<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:set var="n"><portlet:namespace/></c:set>
<div id="${n}currentCookies">
<c:choose>
<c:when test="${empty cookieList}">
<p>No cookies present.</p>
</c:when>
<c:otherwise>
Found ${cookieListSize} cookies:
<table>
<thead>
<tr>
<th>Name</th>
<th>Value</th>
<th>Comment</th>
<th>Domain</th>
<th>Max Age (seconds)</th>
<th>Path</th>
<th>Secure</th>
<th>Version</th>
</tr>
</thead>
<tbody>
<c:forEach items="${cookieList}" var="c">
<tr>
<td>${c.name}</td>
<td>${c.value}</td>
<td>${c.comment}</td>
<td>${c.domain}</td>
<td>${c.maxAge}</td>
<td>${c.path}</td>
<td>${c.secure}</td>
<td>${c.version}</td>
</tr>
</c:forEach>
</tbody>
</table>
</c:otherwise>
</c:choose>
</div>
<hr/>
<div id="${n}createRandomCookie">
<%-- name="randomCookieAction" --%>
<portlet:actionURL var="randomCookieAction" name="randomCookieAction">
<portlet:param name="javax.portlet.action" value="randomCookieAction"/>
</portlet:actionURL>
<form:form action="${randomCookieAction}">
<input type="submit" value="Generate New Random Cookie"/>
</form:form>
</div>
<hr/>
<div id="${n}createFormCookie">
<%-- name="formCookieAction" --%>
<portlet:actionURL var="formCookieAction" >
<portlet:param name="javax.portlet.action" value="formCookieAction"/>
</portlet:actionURL>
<form:form action="${formCookieAction}" commandName="command">
<table>
<tr>
<td colspan="2"><form:errors/></td>
</tr>
<tr>
<td><label for="name">Name:</label></td><td><form:input path="name"/></td>
</tr>
<tr>
<td><label for="value">Value:</label></td><td><form:input path="value"/></td>
</tr>
<tr>
<td><label for="comment">Comment:</label></td><td><form:input path="comment"/></td>
</tr>
<tr>
<td><label for="domain">Domain:</label></td><td><form:input path="domain"/></td>
</tr>
<tr>
<td><label for="maxAge">Max Age (seconds):</label></td><td><form:input path="maxAge"/></td>
</tr>
<tr>
<td><label for="path">Path:</label></td><td><form:input path="path"/></td>
</tr>
<tr>
<td><label for="secure">Secure:</label></td><td><form:input path="secure"/></td>
</tr>
<tr>
<td><label for="version">Version:</label></td><td><form:input path="version"/></td>
</tr>
<tr>
<td colspan="2"><input type="submit" value="Generate Cookie With These Values"/></td>
</tr>
</table>
</form:form>
</div>
