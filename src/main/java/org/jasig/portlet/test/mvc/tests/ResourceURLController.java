/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.portlet.test.mvc.tests;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.portlet.context.PortletContextAware;

/**
 * Simple {@link Controller} that responds to test resource urls.
 * 
 * @author Nicholas Blair
 * @version $Id$
 */
@Controller("resourceURLTest")
@RequestMapping(value = {"VIEW", "EDIT", "HELP", "ABOUT"}, params="currentTest=resourceURLTest")
public class ResourceURLController extends BasePortletTest implements PortletContextAware {
    private PortletContext portletContext;
    
    @Override
    public void setPortletContext(PortletContext portletContext) {
        this.portletContext = portletContext;
    }

    @Override
    public String getTestName() {
        return "Resource URL Test";
    }

    @RequestMapping
	public String displayBasicTestView() {
		return "resourceUrlTest";
	}
	
	@ResourceMapping(value="basicJsonResourceUrl")
	public String handleBasicJsonResourceUrl(ModelMap model) {
		model.addAttribute("hello", "world");
		model.addAttribute("currentTime", new Date().toString());
		return "jsonView";
	}
	
	@RequestMapping(params="testname=param")
	public String displayParameterTestView(ModelMap model, 
			@RequestParam(value="renderParameter", required=false, defaultValue="") String renderParam) {
		model.addAttribute("existingRenderParameterValue", renderParam);
		return "resourceParamTest";
	}
	
	@ResourceMapping(value="paramJsonResourceUrl")
	public String handleParamResourceUrl(ResourceRequest request, ModelMap model) {
		// previous render params
		Map<String, String[]> previousRenderParams = request.getPrivateRenderParameterMap();
		// resource parameter
		Map<String, String[]> resourceParams = request.getPrivateParameterMap();
		
		String [] renderParams = previousRenderParams.get("renderParameter");
		if(renderParams == null) {
			model.addAttribute("renderParameter", "");
		} else {
			model.addAttribute("renderParameter", renderParams[0]);
		}
		
		model.addAttribute("resourceParameter", Arrays.toString(resourceParams.get("resourceParameter")));
		return "jsonView";
	}
    
    @ResourceMapping(value = "resourceInclude")
    public void handleResourceInclude(ResourceRequest request, ResourceResponse response) throws Exception {
        final String dynamicForm = "/WEB-INF/jsp/resourceInclude.jsp";
        
        if (portletContext.getResourceAsStream(dynamicForm) != null) {

            try {
                // dispatch view request to dynamicForm.jsp
                PortletRequestDispatcher dispatcher = portletContext.getRequestDispatcher(dynamicForm);
                dispatcher.include(request, response);

            }
            catch (IOException e) {
                logger.info("GatewayController serveResource exception.");
                throw new PortletException("GatewayController.serveResource exception", e);
            }

        }
        else {
            logger.info("GatewayController_dynamicForm.jsp missing.");
            throw new PortletException("GatewayController_dynamicForm.jsp missing.");
        }
    }
    
    @ResourceMapping(value="resourceForwardServlet")
    public void handleResourceForwardServlet(ResourceRequest request, ResourceResponse response) throws IOException, PortletException {
        final PortletRequestDispatcher servletDispatcher = portletContext.getRequestDispatcher("/SimpleServlet");
        servletDispatcher.forward(request, response);
    }
    
    @ResourceMapping(value="resourceSetHeadersStatus")
    public void handleResourceSetHeadersStatus(ResourceRequest request, ResourceResponse response) throws IOException, PortletException {
        response.setProperty(ResourceResponse.HTTP_STATUS_CODE, "201");
        
        response.setContentType("text/plain");
        
        response.setProperty("SimpleHeader", "SimpleValue");
        response.setProperty("SimpleIntHeader", "1234");
        response.setProperty("SimpleDateHeader", "System.currentTimeMillis()");
        
        final PrintWriter writer = response.getWriter();
        writer.println("Simple Servlet Content: ");
        writer.println(new Date()); 
    }
	
	@ActionMapping
    public void noopAction() {
    }
    
}
