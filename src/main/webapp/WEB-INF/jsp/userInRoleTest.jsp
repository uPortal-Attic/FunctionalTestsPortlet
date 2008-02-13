<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ include file="/WEB-INF/jsp/testSelectorHeader.jsp" %>

<table border="1">
    <thead>
        <tr>
            <th colspan="2">Role</th>
            <th colspan="2">Role Link</th>
        </tr>
        <tr>
            <th>Name</th>
            <th>In Role</th>
            <th>Name</th>
            <th>In Role</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="roleEntry" items="${roles}">
            <tr>
                <td>${roleEntry.key}</td>
                <td>${roleTests[roleEntry.key]}</td>
                <td>${roleEntry.value}</td>
                <td>${roleLinkTests[roleEntry.value]}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
