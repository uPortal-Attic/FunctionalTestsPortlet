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