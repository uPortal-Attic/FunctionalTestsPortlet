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

<%@ include file="/WEB-INF/jsp/testSelectorHeader.jsp" %>

<table>
    <tr>
        <th/>
        <th>Current</th>
        <th>Action</th>
    </tr>
    <tr>
        <th>WindowState</th>
        <td>${renderRequest.windowState}</td>
        <td>${param.ACTION_WINDOW_STATE}</td>
    </tr>
    <tr>
        <th>PortletMode</th>
        <td>${renderRequest.portletMode}</td>
        <td>${param.ACTION_PORTLET_MODE}</td>
    </tr>
</table>

<c:if test="${not empty param.REQUESTED_PORTLET_MODE_NOT_ALLOWED}">
    <p>PortletMode '${param.REQUESTED_PORTLET_MODE_NOT_ALLOWED}' can not be switched to by this portlet.</p>
</c:if>
<c:if test="${not empty param.REQUESTED_WINDOW_STATE_NOT_ALLOWED}">
    <p>WindowState '${param.REQUESTED_WINDOW_STATE_NOT_ALLOWED}' can not be switched to by this portlet.</p>
</c:if>

<table>
    <tr>
        <th/>
        <th>RenderURL</th>
        <th>ActionURL</th>
        <th>processAction</th>
    </tr>
    <c:forEach var="portletMode" items="${supportedPortletModes}">
        <tr>
            <th>${portletMode}</th>
            <td>
                <c:catch var="modeRenderUrlException">
                    <portlet:renderURL var="modeRenderUrl" portletMode="${portletMode}" />
                    <a href="${modeRenderUrl}">switch</a>
                </c:catch>
                <c:if test="${not empty modeRenderUrlException}">
                    Failed to generated renderURL for ${portletMode} due to exception: ${modeRenderUrlException.message}
                </c:if>
            </td>
            <td>
                <c:catch var="modeActionUrlException">
                    <portlet:actionURL var="modeActionUrl" portletMode="${portletMode}" />
                    <a href="${modeActionUrl}">switch</a>
                </c:catch>
                <c:if test="${not empty modeActionUrlException}">
                    Failed to generated actionURL for ${portletMode} due to exception: ${modeActionUrlException.message}
                </c:if>
            </td>
            <td>
                <portlet:actionURL var="modeActionUrlParam">
                    <portlet:param name="REQUESTED_PORTLET_MODE" value="${portletMode}"/>
                </portlet:actionURL>
                <a href="${modeActionUrlParam}">switch</a>
            </td>
        </tr>
    </c:forEach>
    <c:forEach var="windowState" items="${supportedWindowStates}">
        <tr>
            <th>${windowState}</th>
            <td>
                <c:catch var="stateRenderUrlException">
                    <portlet:renderURL var="stateRenderUrl" windowState="${windowState}" />
                    <a href="${stateRenderUrl}">switch</a>
                </c:catch>
                <c:if test="${not empty stateRenderUrlException}">
                    Failed to generated renderURL for ${windowState} due to exception: ${stateRenderUrlException.message}
                </c:if>
            </td>
            <td>
                <c:catch var="stateActionUrlException">
                    <portlet:actionURL var="stateActionUrl" windowState="${windowState}" />
                    <a href="${stateActionUrl}">switch</a>
                </c:catch>
                <c:if test="${not empty stateActionUrlException}">
                    Failed to generated actionURL for ${windowState} due to exception: ${stateActionUrlException.message}
                </c:if>
            </td>
            <td>
                <portlet:actionURL var="stateActionUrlParam">
                    <portlet:param name="REQUESTED_WINDOW_STATE" value="${windowState}"/>
                </portlet:actionURL>
                <a href="${stateActionUrlParam}">switch</a>
            </td>
        </tr>
    </c:forEach>
</table>