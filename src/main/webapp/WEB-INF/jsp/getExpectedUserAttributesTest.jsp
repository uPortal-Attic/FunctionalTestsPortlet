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