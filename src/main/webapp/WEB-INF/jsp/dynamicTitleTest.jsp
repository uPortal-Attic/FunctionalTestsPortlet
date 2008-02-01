<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ include file="/WEB-INF/jsp/testSelectorHeader.jsp" %>

<ul>
    <li>Action Title: '${ACTION_TITLE}'</li>
    <li>Render Title: '${RENDER_TITLE}'</li>
</ul>

<portlet:renderURL var="renderRefreshUrl" />
<portlet:actionURL var="actionRefreshUrl" />
<a href="${renderRefreshUrl}">Render Refresh</a> - <a href="${actionRefreshUrl}">Action Refresh</a>