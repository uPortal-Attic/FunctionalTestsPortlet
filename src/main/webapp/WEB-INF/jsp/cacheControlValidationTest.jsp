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
</style>

<div id="${n}cacheControlState">
<p><button id="${n}jsonValidationTrigger">Get JSON Content that sets CacheControl etag</button></p>
<ul>
<li>Resource URL: ${jsonValidationUrl}</li>
<li>Test Result: <span id="${n}teststatus2"></span></li>
</ul>
<p>JSON data received:</p>
<div class="testoutput">
<p><span id="${n}testresults2"></span></p>
</div>

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
         			success: function(data, textStatus, xhr) {
           		  		if(null != data && data.hello) {
           		  			etag = xhr.getResponseHeader("ETag");
           	  				$('#${n}teststatus2').text('Success');
                     		$('#${n}testresults2').text('Time content was originally rendered: ' + data.timeRendered + ', http status code: ' + xhr.status + ', etag: ' + etag);	
             			} else {
             				$('#${n}teststatus2').text('Failed, no data returned, or missing "hello" element in JSON, http status code: ' + xhr.status);
             			}
         			},
         			error: function(xhr, textStatus, errorThrown) {
         				$('#${n}teststatus2').text('Failed with AJAX error, HTTP status code: ' + xhr.status);
         			}
    			});
			});
    });
});
</script>