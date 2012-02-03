<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<ul>
<li>Does this portal correctly advertise support for head output (javax.portlet.PortalContext#MARKUP_HEAD_ELEMENT_SUPPORT)? <span class="headerTestResult">${headElementSupportProperlyAdvertised}</span></li>
<li>Does this portal correctly set the value for for javax.portlet.PortletRequest#RENDER_PART when rendering the portlet body markup? <span class="headerTestResult">${portletRequestRenderPartValueForBody}</span></li>
<li>If the header was properly rendered, the following text should be GREEN: <span id="headerTestCustomText" class="headerTestResult">Hello World!</span></li>
</ul>