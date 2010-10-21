<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:set var="n"><portlet:namespace/></c:set>
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