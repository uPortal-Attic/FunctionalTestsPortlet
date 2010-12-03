<%@ include file="/WEB-INF/jsp/include.jsp" %>

<ul>
<li>Does this portal correctly advertise support for head output (javax.portlet.PortalContext#MARKUP_HEAD_ELEMENT_SUPPORT)? <span class="headerTestResult">${headElementSupportProperlyAdvertised}</span></li>
<li>Does this portal correctly set the value for for javax.portlet.PortletRequest#RENDER_PART when rendering the portlet body markup? <span class="headerTestResult">${portletRequestRenderPartValueForBody}</span></li>
<li>If the header was properly rendered, the following text should be GREEN: <span id="headerTestCustomText" class="headerTestResult">Hello World!</span></li>
</ul>