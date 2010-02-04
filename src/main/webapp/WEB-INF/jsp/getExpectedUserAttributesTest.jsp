<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ include file="/WEB-INF/jsp/testSelectorHeader.jsp" %>

<h3>Action Request Attributes:</h3>
<table>
    <tr>
        <td>AttributeNames</td>
        <td>${ACTION_AttributeNames}</td>
    </tr>
    <tr>
        <td>USER_INFO</td>
        <td>${ACTION_USER_INFO}</td>
    </tr>
    <tr>
        <td>USER_INFO_MULTIVALUED</td>
        <td>${ACTION_USER_INFO_MULTIVALUED}</td>
    </tr>
</table>

<h3>Render Request Attributes:</h3>
<table>
    <tr>
        <td>AttributeNames</td>
        <td>${RENDER_AttributeNames}</td>
    </tr>
    <tr>
        <td>USER_INFO</td>
        <td>${RENDER_USER_INFO}</td>
    </tr>
    <tr>
        <td>USER_INFO_MULTIVALUED</td>
        <td>${RENDER_USER_INFO_MULTIVALUED}</td>
    </tr>
</table>

<portlet:renderURL var="renderRefreshUrl" />
<portlet:actionURL var="actionRefreshUrl" />
<a href="${renderRefreshUrl}">Render Refresh</a> - <a href="${actionRefreshUrl}">Action Refresh</a>