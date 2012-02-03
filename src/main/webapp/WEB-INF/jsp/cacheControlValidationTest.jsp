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

<portlet:resourceURL id="jsonValidationCachingTest" var="jsonValidationUrl" escapeXml="false"></portlet:resourceURL>
<style type="text/css">
.testoutput {
width: 100%;
height: 5em;
overflow: auto;
border: 1px solid gray;
}
.testdata {
font-weight: bold;
}
</style>

<div id="${n}cacheControlState">
<p><button id="${n}jsonValidationTrigger">Get JSON Content that sets CacheControl etag</button>&nbsp;<button id="${n}sendInvalidEtag" title="Click this button to sent a bogus ETag value; you'll still get content from the cache, but it'll be with a 200 status code">Get JSON Content but set wrong If-None-Match</button></p>
<ul>
<li>Resource URL: ${jsonValidationUrl}</li>
<li>HTTP Response code: <span class="testdata" id="${n}testhttpstatus"></span></li>
<li>Test Result: <span class="testdata" id="${n}teststatus"></span></li>
</ul>
<p>JSON data received:</p>
<div class="testoutput">
<p><span id="${n}testresults"></span></p>
</div>

<ul>
<li>Per the XMLHttpRequest spec (<a href="http://www.w3.org/TR/XMLHttpRequest/">http://www.w3.org/TR/XMLHttpRequest/</a>)<br/>
<pre> 
For 304 Not Modified responses that are a result of a user agent generated conditional request the user agent must act as if the server 
gave a 200 OK response with the appropriate content.
</pre>
</li>
<li>jQuery .ajax() follows this guideline; use Firebug to verify the 304 status code is returned properly.</li>
</ul>
</div>

<script type="text/javascript">
up.jQuery(function() {
    var $ = up.jQuery;
    $(document).ready(function(){
			$('#${n}jsonValidationTrigger').click(function() {
    			$.ajax({
         			url: '${jsonValidationUrl}',
         			type: "GET",
         			dataType: "json",
         			complete: function(xhr) {
         				$('#${n}testhttpstatus').text(xhr.status);
             		},
         			success: function (data, textStatus, xhr) {
         				if(null != data && data.hello) {
           		  			etag = xhr.getResponseHeader("ETag");
           	  				$('#${n}teststatus').text('Success');
                     		$('#${n}testresults').text('Time content was originally rendered: ' + data.timeRendered + ', etag: ' + etag);	
             			} else {
             				$('#${n}teststatus').text('Failed, no data returned, or missing "hello" element in JSON, http status code: ' + xhr.status);
             			}
             		},
         			error: function(xhr, textStatus, errorThrown) {
         				$('#${n}teststatus').text('Failed with AJAX error, HTTP status code: ' + xhr.status);
         			}
    			});
			});
			
			$('#${n}sendInvalidEtag').click(function() {
    			$.ajax({
    				beforeSend: function(req) {
    			        req.setRequestHeader("If-None-Match", "wrongvalue");
    			    },
         			url: '${jsonValidationUrl}',
         			type: "GET",
         			dataType: "json",
         			complete: function(xhr) {
         				$('#${n}testhttpstatus').text(xhr.status);
             		},
         			success: function (data, textStatus, xhr) {
         				if(null != data && data.hello) {
           		  			etag = xhr.getResponseHeader("ETag");
           	  				$('#${n}teststatus').text('Success');
                     		$('#${n}testresults').text('Time content was originally rendered: ' + data.timeRendered + ', etag: ' + etag);	
             			} else {
             				$('#${n}teststatus').text('Failed, no data returned, or missing "hello" element in JSON, http status code: ' + xhr.status);
             			}
             		},
         			error: function(xhr, textStatus, errorThrown) {
         				$('#${n}teststatus').text('Failed with AJAX error, HTTP status code: ' + xhr.status);
         			}
    			});
			});
    });
});
</script>