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

<portlet:actionURL var="simpleEventTestUrl">
  <portlet:param name="javax.portlet.action" value="simpleEventTest"/>
</portlet:actionURL>
<portlet:actionURL var="endlessEventTestUrl">
  <portlet:param name="javax.portlet.action" value="endlessEventTest"/>
</portlet:actionURL>
<portlet:renderURL var="refreshUrl" />

<div>
  <h3>Simple Portlet Event</h3>
  <a href="${simpleEventTestUrl}">Test Simple Event</a>
  <c:choose>
    <c:when test="${empty simpleEvent}">
      <div>No Simple Event Data</div>
    </c:when>
    <c:otherwise>
      <div>Simple Event: ${simpleEvent}</div>
    </c:otherwise>
  </c:choose>
</div>

<div>
  <h3>Endless Portlet Event</h3>
  <a href="${endlessEventTestUrl}">Test Endless Event</a>
  <c:choose>
    <c:when test="${empty endlessEvent}">
      <div>No Endless Event Data</div>
    </c:when>
    <c:otherwise>
      <div>Endless Event: ${endlessEvent}</div>
    </c:otherwise>
  </c:choose>
</div>