<%--

    Licensed to Apereo under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Apereo licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ include file="/WEB-INF/jsp/testSelectorHeader.jsp" %>

<%@ include file="/WEB-INF/jsp/resourceTestHeader.jsp" %>

<portlet:resourceURL id="basicJsonResourceUrl" var="basicJsonResourceUrl" escapeXml="false" />
<portlet:resourceURL id="resourceInclude" var="resourceIncludeUrl" escapeXml="false" />
<portlet:resourceURL id="resourceForwardServlet" var="resourceForwardServletUrl" escapeXml="false" />
<portlet:resourceURL id="resourceSetHeadersStatus" var="resourceSetHeadersStatusUrl" escapeXml="false" />
<style type="text/css">
.testoutput {
width: 100%;
height: 5em;
overflow: auto;
border: 1px solid gray;
}
</style>

<p>This basic test will retrieve static data via a Portlet 2.0 Resource URL and populate the text area below.</p>
<p><button id="${n}testtrigger">Run Test</button></p>
<p><button id="${n}testIncludeTrigger">Run JSP Include Test</button></p>
<p><button id="${n}testServletForwardTrigger">Run Servlet Forward Test</button></p>
<p><button id="${n}testSetHeadersTest">Run Set Headers Test</button></p>
<ul>
<li>Resource URL: <span id="${n}testurl"></span></li>
<li>Test Result: <span id="${n}teststatus"></span></li>
</ul>
<p>Data received:</p>
<div class="testoutput">
<p><span id="${n}testheaders"></span></p>
<p><span id="${n}testresults"></span></p>
</div>

<script type="text/javascript">
up.jQuery(function() {
    var $ = up.jQuery;
    $(document).ready(function(){
        doTestCall = function(url) {
            $('#${n}testurl').text(url);
            $.ajax({
                url: url,
                type: "GET",
                dataType: "text",
                success: function(data, textStatus, xhr) {
                    $('#${n}teststatus').text(xhr.status + ' ' + xhr.statusText);
                    $('#${n}testheaders').text(xhr.getAllResponseHeaders());
                    
                    if(null != data) {
                        $('#${n}testresults').text(data); 
                    } else {
                        $('#${n}teststatus').text('NO DATA RETURNED');
                    }
                },
                error: function(xhr, textStatus, errorThrown) {
                    $('#${n}teststatus').text('Failed with AJAX error: ' + errorThrown);
                }
            });
        };
        
		
		$('#${n}testtrigger').click(function() {
		    doTestCall('${basicJsonResourceUrl}');
		});
      
        $('#${n}testIncludeTrigger').click(function() {
            doTestCall('${resourceIncludeUrl}');
        });
    
        $('#${n}testServletForwardTrigger').click(function() {
            doTestCall('${resourceForwardServletUrl}');
        });
    
        $('#${n}testSetHeadersTest').click(function() {
            doTestCall('${resourceSetHeadersStatusUrl}');
        });
		
    });
});
</script>