<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ include file="/WEB-INF/jsp/testSelectorHeader.jsp" %>

<h3>Parameter Based Exception</h3>
<p>
    Throw an exception that is caused by the existence of a render or action parameter. This type
    of exception should be cleared by a <b>refresh</b> in the error channel.
    <br>
    <portlet:renderURL var="renderParameterExceptionUrl">
        <portlet:param name="ThrowExceptionParameter" value="true"/>
    </portlet:renderURL>
    <a href="${renderParameterExceptionUrl}">Throw via render</a>
    
    <portlet:actionURL var="actionParameterExceptionUrl">
        <portlet:param name="ThrowExceptionParameter" value="true"/>
    </portlet:actionURL>
    <a href="${actionParameterExceptionUrl}">Throw via action</a>
</p>

<h3>Portlet Session Based Exception</h3>
<p>
    Throw an exception that is caused by the existence of a portlet scoped session attribute or
    This type of exception should only be cleared by a <b>restart</b> in the error channel.
    <br>
    <portlet:renderURL var="renderPortletSessionExceptionUrl">
        <portlet:param name="ThrowExeceptionPortletSession" value="true"/>
    </portlet:renderURL>
    <a href="${renderPortletSessionExceptionUrl}">Throw via render</a>
    
    <portlet:actionURL var="actionPortletSessionExceptionUrl">
        <portlet:param name="ThrowExeceptionPortletSession" value="true"/>
    </portlet:actionURL>
    <a href="${actionPortletSessionExceptionUrl}">Throw via action</a>
</p>

<h3>Application Session Based Exception</h3>
<p>
    Throw an exception that is caused by the existence of a application scoped session attribute or
    This type of exception should only be cleared by logging out and back into the portal.
    <br>
    <portlet:renderURL var="renderApplicationSessionExceptionUrl">
        <portlet:param name="ThrowExeceptionApplicationSession" value="true"/>
    </portlet:renderURL>
    <a href="${renderApplicationSessionExceptionUrl}">Throw via render</a>
    
    <portlet:actionURL var="actionApplicationSessionExceptionUrl">
        <portlet:param name="ThrowExeceptionApplicationSession" value="true"/>
    </portlet:actionURL>
    <a href="${actionApplicationSessionExceptionUrl}">Throw via action</a>
</p>