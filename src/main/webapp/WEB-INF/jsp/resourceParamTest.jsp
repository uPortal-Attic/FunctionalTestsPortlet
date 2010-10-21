<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ include file="/WEB-INF/jsp/resourceTestHeader.jsp" %>

<c:set var="n"><portlet:namespace/></c:set>
<portlet:resourceURL id="paramJsonResourceUrl" var="paramJsonResourceUrl" escapeXml="false">
<portlet:param name="renderParameter" value="${existingRenderParameterValue}"/>
<portlet:param name="resourceParameter" value="hello"/>
</portlet:resourceURL>
<style type="text/css">
.testoutput {
width: 100%;
height: 5em;
overflow: auto;
border: 1px solid gray;
}
</style>

<p>This test is similar to the Basic test, only validating the presence of render and adding resource parameters.</p>

<portlet:renderURL var="reRenderUrl">
<portlet:param name="testname" value="param"/>
<portlet:param name="renderParameter" value="somethingDifferent"/>
</portlet:renderURL>

<p><a href="${reRenderUrl}">Click this link to reload this view with a different value for the render parameter set</a></p>

<p><button id="${n}testtrigger">Request JSON Data</button></p>
<ul>
<li>Resource URL: ${paramJsonResourceUrl}</li>
<li>Test Result: <span id="${n}teststatus"></span></li>
</ul>
<p>JSON data received:</p>
<div class="testoutput">
<p><span id="${n}testresults"></span></p>
</div>

<script type="text/javascript">
up.jQuery(function() {
    var $ = up.jQuery;
    $(document).ready(function(){		
			$('#${n}testtrigger').click(function() {
    			$.ajax({
         			url: '${paramJsonResourceUrl}',
         			type: "GET",
         			dataType: "json",
         			success: function(data) {
           		  		if(null != data && data.resourceParameter) {
           		  	    $('#${n}testresults').text('renderParameter: ' + data.renderParameter + ', resourceParameter: ' + data.resourceParameter);
               		  		if('${existingRenderParameterValue}' == data.renderParameter) {
               		  		  $('#${n}teststatus').text('Success');
               		  		} else {
               		  	    $('#${n}teststatus').text('Failed, renderParameter does not match expected value ${existingRenderParameterValue}');
               		  		}
             			} else {
             				$('#${n}teststatus').text('Failed, no data returned, or missing parameters in JSON');
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