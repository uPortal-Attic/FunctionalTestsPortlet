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
<table width="100%">
  <tr>
    <td><a href="<portlet:renderURL><portlet:param name="testname" value="expiration"/></portlet:renderURL>">CacheControl Expiration Method Test</a></td>
    <td><a href="<portlet:renderURL><portlet:param name="testname" value="validation"/></portlet:renderURL>">CacheControl Validation Method (ETag) Test</a></td>
    <td><a href="<portlet:actionURL name="cacheClear"/>">Clear all cached content for this portlet (Action URL)</a></td>
  </tr>
</table>
<hr/>