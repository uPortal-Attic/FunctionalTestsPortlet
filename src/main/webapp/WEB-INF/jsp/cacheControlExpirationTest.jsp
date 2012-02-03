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

<%@ include file="/WEB-INF/jsp/cacheControlTestHeader.jsp" %>

<portlet:resourceURL id="jsonExpirationCachingTest" var="jsonExpirationUrl" escapeXml="false"></portlet:resourceURL>
<style type="text/css">
.testoutput {
width: 100%;
height: 5em;
overflow: auto;
border: 1px solid gray;
}
</style>

<div id="${n}cacheControlState">
<p><button id="${n}jsonExpirationTrigger">Get JSON Content that sets CacheControl expiration</button></p>
<ul>
<li>Resource URL: ${jsonExpirationUrl}</li>
<li>Test Result: <span id="${n}teststatus1"></span></li>
</ul>
<p>JSON data received:</p>
<div class="testoutput">
<p><span id="${n}testresults1"></span></p>
</div>

</div>

<script type="text/javascript">
up.jQuery(function() {
    var $ = up.jQuery;
    $(document).ready(function(){
		
			$('#${n}jsonExpirationTrigger').click(function() {
    			$.ajax({
         			url: '${jsonExpirationUrl}',
         			type: "GET",
         			dataType: "json",
         			success: function(data, textStatus, xhr) {
           		  		if(null != data && data.hello) {
           	  				$('#${n}teststatus1').text('Success');
                     		$('#${n}testresults1').text('Time content was originally rendered: ' + data.timeRendered + ', http status code: ' + xhr.status);	
             			} else {
             				$('#${n}teststatus1').text('Failed, no data returned, or missing "hello" element in JSON, http status code: ' + xhr.status);
             			}
         			},
         			error: function(xhr, textStatus, errorThrown) {
         				$('#${n}teststatus1').text('Failed with AJAX error, HTTP status code: ' + xhr.status);
         			}
    			});
			});

    });
});
</script>