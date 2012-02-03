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
