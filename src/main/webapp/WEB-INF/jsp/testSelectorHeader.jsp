<%@ include file="/WEB-INF/jsp/include.jsp" %>

<portlet:actionURL var="testSelectorFormUrl" />
<table width="100%">
    <tr>
        <td width="50%">
            <form action="${testSelectorFormUrl}" method="POST">
                <select name="currentTest">
                    <c:forEach var="portletTest" items="${portletTests}">
                        <c:set var="selected" value="" />
                        <c:if test="${portletTest.testKey == portletSessionScope['currentTest']}">
                            <c:set var="selected" value='selected="selected"' />
                        </c:if>
                    
                        <option value="${portletTest.testKey}" ${selected}>${portletTest.testName}</option>
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
