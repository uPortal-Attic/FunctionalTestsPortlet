<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ include file="/WEB-INF/jsp/testSelectorHeader.jsp" %>

<portlet:actionURL var="setEmptyAndNullUrl" name="setEmptyAndNull" />
<a href="${setEmptyAndNullUrl}">Set Empty and Null Preferences</a>

<table>
    <tbody>
        <tr>
            <th/>
            <th>getValue</th>
            <th>getValues</th>
        </tr>
        <tr>
            <td align="right">NULL_PREFERENCES</td>
            <td>${NULL_PREFERENCES_value}</td>
            <td>${NULL_PREFERENCES_values}</td>
        </tr>
        <tr>
            <td align="right">MIXED_PREFERENCES</td>
            <td>${MIXED_PREFERENCES_value}</td>
            <td>${MIXED_PREFERENCES_values}</td>
        </tr>
        <tr>
            <td align="right">NULL_PREFERENCE</td>
            <td>${NULL_PREFERENCE_value}</td>
            <td>${NULL_PREFERENCE_values}</td>
        </tr>
        <tr>
            <td align="right">EMPTY_PREFERENCE</td>
            <td>${EMPTY_PREFERENCE_value}</td>
            <td>${EMPTY_PREFERENCE_values}</td>
        </tr>
    </tbody>
</table>
