<portlet:renderURL var="basicTestUrl">
<portlet:param name="testname" value="basic"/>
</portlet:renderURL>
<portlet:renderURL var="parameterTestUrl">
<portlet:param name="testname" value="param"/>
</portlet:renderURL>
<table width="100%">
  <tr>
    <td><a href="${basicTestUrl}">Basic Test</a></td>
    <td><a href="${parameterTestUrl}">Parameter Test</a></td>
  </tr>
</table>