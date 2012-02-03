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
package org.jasig.portlet.test.mvc;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;
import org.springframework.web.portlet.mvc.Controller;

/**
 * Central hub for test execution.
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public class TestDelegatingSelectorController extends AbstractController {
    public static final String CURRENT_TEST_ATTR = TestDelegatingSelectorController.class.getName() + ".CURRENT_TEST";
    public static final String TEST_CONTROLLER_NAMES_ATTR = TestDelegatingSelectorController.class.getName() + ".TEST_CONTROLLER_NAMES";
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private SortedMap<String, Controller> testControllers;
    
    public TestDelegatingSelectorController() {
        this.setRenderWhenMinimized(true);
    }

    /**
     * @return the testControllers
     */
    public Map<String, Controller> getTestControllers() {
        return this.testControllers;
    }
    /**
     * @param testControllers the testControllers to set
     */
    @Required
    public void setTestControllers(Map<String, Controller> testControllers) {
        Validate.notNull(testControllers);
        if (testControllers instanceof SortedMap) {
            this.testControllers = (SortedMap<String, Controller>)testControllers;
        }
        else {
            this.testControllers = new TreeMap<String, Controller>(testControllers);
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleActionRequestInternal(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    @Override
    protected void handleActionRequestInternal(ActionRequest request, ActionResponse response) throws Exception {
        final String currentTestName = this.getCurrentTestName(request);
        this.logger.debug("handleActionRequestInternal for test '{}' with mode: {} and state: {}", new Object[] { currentTestName, request.getPortletMode(), request.getWindowState() });
        
        //Switch tests if requested
        final String nextTestName = request.getParameter(CURRENT_TEST_ATTR);
        if (nextTestName != null) {
            final Controller nextTest = this.testControllers.get(nextTestName);
            if (nextTest != null) {
                final PortletSession portletSession = request.getPortletSession();
                this.logger.info("Changing to test '{}' from test '{}'", nextTestName, currentTestName);
                portletSession.setAttribute(CURRENT_TEST_ATTR, nextTestName);
            }
            else {
                this.logger.warn("No test Controller named '{}'", nextTestName);
            }
        }
        //No switch requested, delegate the action to the current test
        else {
            this.logger.info("Delegating handleActionRequest to test Controller named '{}'", currentTestName);
            
            final Controller currentTest = this.testControllers.get(currentTestName);
            try {
                currentTest.handleActionRequest(request, response);
            }
            catch (Exception e) {
                this.logger.error("Test '{}' threw an exception during handleActionRequest", currentTestName, e);
                throw e;
            }
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleRenderRequestInternal(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
        final String currentTestName = getCurrentTestName(request);
        request.setAttribute(CURRENT_TEST_ATTR, currentTestName);
        this.logger.debug("handleRenderRequestInternal for test '{}' with mode: {} and state: {}", new Object[] { currentTestName, request.getPortletMode(), request.getWindowState() });

        if (currentTestName != null) {
            response.setTitle(currentTestName);
        }
        
        final Controller currentTest = this.testControllers.get(currentTestName);
        ModelAndView delegateModelAndView;
        try {
            delegateModelAndView = currentTest.handleRenderRequest(request, response);
        }
        catch (Exception e) {
            this.logger.error("Test '{}' threw an exception during handleRenderRequest", currentTestName, e);
            throw e;
        }
        
        if (delegateModelAndView == null) {
            delegateModelAndView = new ModelAndView("testSelectorHeader");
        }
        
        //Add list of test names to model for rendering test selector
        final Map<String, Object> model = delegateModelAndView.getModel();
        model.put(TEST_CONTROLLER_NAMES_ATTR, this.testControllers.keySet());

        return delegateModelAndView;
    }

    protected String getCurrentTestName(PortletRequest request) {
        final PortletSession portletSession = request.getPortletSession();
        String currentTestName = (String)portletSession.getAttribute(CURRENT_TEST_ATTR);
        
        if (currentTestName == null) {
            currentTestName = this.testControllers.firstKey();
        }
        
        return currentTestName;
    }
}
