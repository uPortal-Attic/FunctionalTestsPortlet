<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ include file="/WEB-INF/jsp/testSelectorHeader.jsp" %>

<table>
    <tbody>
        <tr>
            <td align="right">Prefered Request Locale: </td>
            <td>${preferedRequestLocale}</td>
        </tr>
        <tr>
            <td align="right">Request Locales: </td>
            <td>
                <ol>
                    <c:forEach var="requestLocale" items="${requestLocales}">
                        <li>${requestLocale}</li>
                    </c:forEach>
                </ol>
            </td>
        </tr>
        <tr>
            <td align="right">Response Locale: </td>
            <td>${responseLocale}</td>
        </tr>
    </tbody>
</table>
