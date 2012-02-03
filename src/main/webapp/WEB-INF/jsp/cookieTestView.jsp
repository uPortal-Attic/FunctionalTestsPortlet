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

<style type="text/css">
#${n}_overflowwrap {
overflow:scroll;
}
</style>
<div id="${n}_overflowwrap">
<div id="${n}currentCookies">
<c:choose>
<c:when test="${empty cookieList}">
<p>No cookies present.</p>
</c:when>
<c:otherwise>
Found ${cookieListSize} cookies:
<table>
<thead>
<tr>
<th>Name</th>
<th>Value</th>
<th>Comment</th>
<th>Domain</th>
<th>Max Age (sec)</th>
<th>Path</th>
<th>Secure</th>
<th>Version</th>
<th>Edit?</th>
<th>Delete?</th>
</tr>
</thead>
<tbody>
<c:forEach items="${cookieList}" var="c">
<tr class="editform">
<td class="cookieName"><input type="hidden" name="name" value="${c.name}"/>${c.name}</td>
<td class="cookieValue"><input type="text" name="value" value="${c.value}"/></td>
<td class="cookieComment"><input type="text" name="comment" value="${c.comment}"/></td>
<td class="cookieDomain"><input type="text" name="domain" value="${c.domain}"/></td>
<td class="cookieMaxAge"><input type="text" name="maxAge" value="${c.maxAge}" style="width: 4em;"/></td>
<td class="cookiePath"><input type="text" name="path" value="${c.path}"/></td>
<td class="cookieSecure"><input type="text" name="secure" value="${c.secure}"/></td>
<td class="cookieVersion"><input type="text" name="version" value="${c.version}" style="width: 2em;"/></td>
<td><input class="editTrigger" type="submit" value="Save"/></td>
<td><input class="deleteTrigger" type="submit" value="Delete"/></td>
</tr>
</c:forEach>
</tbody>
</table>
</c:otherwise>
</c:choose>
</div>
<hr/>
<div id="${n}createRandomCookie">
<portlet:actionURL var="randomCookieAction" name="randomCookieAction"/>
<form:form action="${randomCookieAction}" htmlEscape="false">
<input type="submit" value="Generate New Random Cookie"/>
</form:form>
</div>
<hr/>
<div id="${n}createFormCookie">
<portlet:actionURL var="formCookieAction" name="formCookieAction"/>
<form:form action="${formCookieAction}" commandName="command" htmlEscape="false" method="post">
<table>
<tr>
<td colspan="2"><form:errors/></td>
</tr>
<tr>
<td><label for="name">Name:</label></td><td><form:input path="name"/></td>
</tr>
<tr>
<td><label for="value">Value:</label></td><td><form:input path="value"/></td>
</tr>
<tr>
<td><label for="comment">Comment:</label></td><td><form:input path="comment"/></td>
</tr>
<tr>
<td><label for="domain">Domain:</label></td><td><form:input path="domain"/></td>
</tr>
<tr>
<td><label for="maxAge">Max Age (seconds):</label></td><td><form:input path="maxAge"/></td>
</tr>
<tr>
<td><label for="path">Path:</label></td><td><form:input path="path"/></td>
</tr>
<tr>
<td><label for="version">Version:</label></td><td><form:radiobutton path="version" value="0" label="0"/><form:radiobutton path="version" value="1" label="1"/></td>
</tr>
<tr>
<td><label for="secure">Secure:</label></td><td><form:input path="secure"/></td>
</tr>
<tr>
<td colspan="2"><input type="submit" value="Generate Cookie With These Values"/></td>
</tr>
</table>
</form:form>
</div>

<portlet:actionURL var="editCookieAction" name="editCookieAction"/>
<div id="${n}hiddenForm">
<form id="${n}hiddenEditForm" action="${editCookieAction }" method="post">
<input class="cookieName" type="hidden" name="name" value=""/>
<input class="cookieValue" type="hidden" name="value" value=""/>
<input class="cookieComment" type="hidden" name="comment" value=""/>
<input class="cookieDomain" type="hidden" name="domain" value=""/>
<input class="cookieMaxAge" type="hidden" name="maxAge" value=""/>
<input class="cookiePath" type="hidden" name="path" value=""/>
<input class="cookieSecure" type="hidden" name="secure" value=""/>
<input class="cookieVersion" type="hidden" name="version" value=""/>
</form>
</div>
</div>


<script type="text/javascript">
up.jQuery(function() {
    var $ = up.jQuery;
    $(document).ready(function(){
        $('#${n}hiddenForm').hide();

        var hiddenEditForm = $('#${n}hiddenEditForm');
		$('.deleteTrigger').click(function(event) {
			event.stopPropagation();
			// set the hidden form's fields to the event target's sibling values
			$('#${n}hiddenEditForm .cookieName').val($(event.target).parent().siblings('.cookieName').children().val());
			$('#${n}hiddenEditForm .cookieValue').val($(event.target).parent().siblings('.cookieValue').children().val());
			$('#${n}hiddenEditForm .cookieComment').val($(event.target).parent().siblings('.cookieComment').children().val());
			$('#${n}hiddenEditForm .cookieDomain').val($(event.target).parent().siblings('.cookieDomain').children().val());
			$('#${n}hiddenEditForm .cookiePath').val($(event.target).parent().siblings('.cookiePath').children().val());
			$('#${n}hiddenEditForm .cookieSecure').val($(event.target).parent().siblings('.cookieSecure').children().val());
			$('#${n}hiddenEditForm .cookieVersion').val($(event.target).parent().siblings('.cookieVersion').children().val());
			// set max age to 0 to delete a cookie
			$('#${n}hiddenEditForm .cookieMaxAge').val(0);
			hiddenEditForm.submit();
		});

		$('.editTrigger').click(function(event) {
			event.stopPropagation();

			// set the hidden form's fields to the event target's sibling values
			$('#${n}hiddenEditForm .cookieName').val($(event.target).parent().siblings('.cookieName').children().val());
			$('#${n}hiddenEditForm .cookieValue').val($(event.target).parent().siblings('.cookieValue').children().val());
			$('#${n}hiddenEditForm .cookieComment').val($(event.target).parent().siblings('.cookieComment').children().val());
			$('#${n}hiddenEditForm .cookieDomain').val($(event.target).parent().siblings('.cookieDomain').children().val());
			$('#${n}hiddenEditForm .cookiePath').val($(event.target).parent().siblings('.cookiePath').children().val());
			$('#${n}hiddenEditForm .cookieSecure').val($(event.target).parent().siblings('.cookieSecure').children().val());
			$('#${n}hiddenEditForm .cookieVersion').val($(event.target).parent().siblings('.cookieVersion').children().val());
			// set max age to 0 to delete a cookie
			$('#${n}hiddenEditForm .cookieMaxAge').val($(event.target).parent().siblings('.cookieMaxAge').children().val());
			hiddenEditForm.submit();
		});
    });
});
</script>
