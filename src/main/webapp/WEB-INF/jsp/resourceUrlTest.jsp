<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ include file="/WEB-INF/jsp/testSelectorHeader.jsp" %>

<%@ include file="/WEB-INF/jsp/resourceTestHeader.jsp" %>

<portlet:resourceURL id="basicJsonResourceUrl" var="basicJsonResourceUrl" escapeXml="false"></portlet:resourceURL>
<style type="text/css">
.testoutput {
width: 100%;
height: 5em;
overflow: auto;
border: 1px solid gray;
}
</style>

<p>This basic test will retrieve static JSON data via a Portlet 2.0 Resource URL and populate the text area below.</p>
<p><button id="${n}testtrigger">Run Test</button></p>
<ul>
<li>Resource URL: ${basicJsonResourceUrl}</li>
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
         			url: '${basicJsonResourceUrl}',
         			type: "GET",
         			dataType: "json",
         			success: function(data) {
           		  		if(null != data && data.hello) {
           	  				$('#${n}teststatus').text('Success');
                     		$('#${n}testresults').text('currentTime: ' + data.currentTime);	
             			} else {
             				$('#${n}teststatus').text('Failed, no data returned, or missing "hello" element in JSON');
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