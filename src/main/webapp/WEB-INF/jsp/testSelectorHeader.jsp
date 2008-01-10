<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@page import="org.jasig.portlet.test.mvc.tests.ExceptionThrowingTest"%>
<%@page import="org.jasig.portlet.test.mvc.TestDelegatingSelectorController"%>
<c:set var="currentTest" value="<%=TestDelegatingSelectorController.CURRENT_TEST_ATTR%>"/>
<c:set var="testControllerNames" value="<%=TestDelegatingSelectorController.TEST_CONTROLLER_NAMES_ATTR%>"/>

<portlet:actionURL var="testSelectorFormUrl" />
<table width="100%">
    <tr>
        <td width="50%">
            <form action="${testSelectorFormUrl}" method="POST">
                <select name="${currentTest}">
                    <c:forEach var="testName" items="${requestScope[testControllerNames]}">
                        <c:set var="selected" value="" />
                        <c:if test="${testName == requestScope[currentTest]}">
                            <c:set var="selected" value='selected="selected"' />
                        </c:if>
                    
                        <option value="${testName}" ${selected}>${testName}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="Switch Test"/>
            </form>
        </td>
        <td width="50%" align="right">
            <portlet:renderURL var="refreshUrl" />
            <a href="${refreshUrl}">Refresh</a>
        </td>
    </tr>
</table>
<hr/>
<br/>
