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
