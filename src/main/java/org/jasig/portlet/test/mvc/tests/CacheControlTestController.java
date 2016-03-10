/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.portlet.test.mvc.tests;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.CacheControl;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.portlet.context.PortletContextAware;

/**
 * Simple form based {@link BasePortletTest} for testing {@link CacheControl} support.
 * 
 * @author Nicholas Blair
 * @version $Id$
 */
@Controller("cacheControlTest")
@RequestMapping(value = {"VIEW", "EDIT", "HELP", "ABOUT"}, params="currentTest=cacheControlTest")
public class CacheControlTestController extends BasePortletTest implements PortletContextAware {
    private PortletContext portletContext;

	@Override
	public String getTestName() {
		return "Cache Control Test";
	}
	
	@Override
    public void setPortletContext(PortletContext portletContext) {
	    this.portletContext = portletContext;
    }

    @RenderMapping
	public String showExpirationTestForm() {
		return "cacheControlExpirationTest";
	}
	
	@RenderMapping(params="testname=validation")
	public String showValidationTestForm() {
		return "cacheControlValidationTest";
	}
    
    @RenderMapping(params="testname=servletForward")
    public String showServletForwardTestForm() {
        return "cacheServletForwardTest";
    }
	
	/**
	 * Writes some simple JSON output. Sets cache expiration time of 120 seconds.
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResourceMapping(value="jsonExpirationCachingTest")
	public void writeJsonContentWithExpirationCaching(ResourceRequest request, ResourceResponse response) throws IOException {
		response.getCacheControl().setExpirationTime(120);
		response.setContentType("application/json");
		PrintWriter writer = new PrintWriter(response.getPortletOutputStream());
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd h:mm:ss");
		writer.write("{ \"hello\": \"true\", \"timeRendered\": \"" + sdf.format(now) + "\" }");
		writer.close();
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResourceMapping(value="jsonValidationCachingTest")
	public void writeJsonContentWithValidationCaching(ResourceRequest request, ResourceResponse response) throws IOException {
		if(request.getETag() != null) {
			response.getCacheControl().setUseCachedContent(true);
			//always must set expirationtime with setUseCachedContent(true); use different value for testing
			response.getCacheControl().setExpirationTime(60);
			return;
		}
		
		response.getCacheControl().setETag(RandomStringUtils.randomAlphanumeric(16));
		// have to set an expiration time when using etag per portlet spec (empty expiration time is treated as "expired")
		response.getCacheControl().setExpirationTime(120);
		
		response.setContentType("application/json");
		PrintWriter writer = new PrintWriter(response.getPortletOutputStream());
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd h:mm:ss");
		writer.write("{ \"hello\": \"true\", \"timeRendered\": \"" + sdf.format(now) + "\" }");
		writer.close();
	}
    
    @ResourceMapping(value="servletForwardingTest")
    public void writeServletForwardingTest(ResourceRequest request, ResourceResponse response) throws IOException, PortletException {
        response.getCacheControl().setETag(RandomStringUtils.randomAlphanumeric(16));
        // have to set an expiration time when using etag per portlet spec (empty expiration time is treated as "expired")
        response.getCacheControl().setExpirationTime(30);
        
        final PortletRequestDispatcher servletDispatcher = portletContext.getRequestDispatcher("/SimpleServlet");
        servletDispatcher.forward(request, response);
    }
	
	/**
	 * Empty action; actions (and events) trigger the portlet renderer to clear the output cache
	 * for this portlet.
	 */
	@ActionMapping("cacheClear")
    public void triggerCacheClear() {
    }
	
	/**
     * Each subclass must of have an empty {@link ActionMapping} annotated method with no value.
     * Method does not have to do anything; it's triggered when a person selects a different
     * test from the drop down list.
     */
	@ActionMapping
	public void emptyAction() {
	}
}
