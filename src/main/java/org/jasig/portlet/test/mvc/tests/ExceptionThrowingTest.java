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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * Provides a way to throw exceptions and base the excpetion being thrown on a request parameter,
 * portlet scoped session attribute, or application scoped session attribute.
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
@Controller("exceptionThrowingTest")
@RequestMapping(value = {"VIEW", "EDIT", "HELP", "ABOUT"}, params="currentTest=exceptionThrowingTest")
public class ExceptionThrowingTest extends BasePortletTest {

    public static final String THROW_EXECEPTION_APPLICATION_SESSION = "ThrowExeceptionApplicationSession";
    public static final String THROW_EXECEPTION_PORTLET_SESSION = "ThrowExeceptionPortletSession";
    public static final String THROW_EXCEPTION_PARAMETER = "ThrowExceptionParameter";

    @Override
    public String getTestName() {
        return "Exception Throwing Test";
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleActionRequestInternal(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    @ActionMapping
    public void handleActionRequestInternal(ActionRequest request, ActionResponse response) throws Exception {
        this.doExceptionThrowing(request);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleRenderRequestInternal(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @RenderMapping
    public ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
        this.doExceptionThrowing(request);

        return new ModelAndView("exceptionThrowingTest");
    }
    
    protected void doExceptionThrowing(PortletRequest request) throws PortletException {
        final PortletSession portletSession = request.getPortletSession();


        final String throwExceptionApplicationSessionParameterStr = request.getParameter(THROW_EXECEPTION_APPLICATION_SESSION);
        if (throwExceptionApplicationSessionParameterStr != null) {
            final boolean throwExceptionApplicationSessionParameter = Boolean.parseBoolean(throwExceptionApplicationSessionParameterStr);
            portletSession.setAttribute(THROW_EXECEPTION_APPLICATION_SESSION, throwExceptionApplicationSessionParameter, PortletSession.APPLICATION_SCOPE);
        }
        
        final Boolean throwExceptionApplicationSession = (Boolean)portletSession.getAttribute(THROW_EXECEPTION_APPLICATION_SESSION, PortletSession.APPLICATION_SCOPE);
        if (throwExceptionApplicationSession != null && throwExceptionApplicationSession) {
            throw new PortletException("(" + this.getFormattedDate() + ") Throwing exception because of APPLICATION scoped session attribute. You will need to log out and back in to clear this error message.");
        }
        

        final String throwExceptionPortletSessionParameterStr = request.getParameter(THROW_EXECEPTION_PORTLET_SESSION);
        if (throwExceptionPortletSessionParameterStr != null) {
            final boolean throwExceptionPortletSessionParameter = Boolean.parseBoolean(throwExceptionPortletSessionParameterStr);
            portletSession.setAttribute(THROW_EXECEPTION_PORTLET_SESSION, throwExceptionPortletSessionParameter, PortletSession.PORTLET_SCOPE);
        }
        
        final Boolean throwExceptionPortletSession = (Boolean)portletSession.getAttribute(THROW_EXECEPTION_PORTLET_SESSION, PortletSession.PORTLET_SCOPE);
        if (throwExceptionPortletSession != null && throwExceptionPortletSession) {
            throw new PortletException("(" + this.getFormattedDate() + ") Throwing exception because of PORTLET scoped session attribute. A Restart should clear this error message.");
        }

        
        final boolean throwExceptionParameter = Boolean.parseBoolean(request.getParameter(THROW_EXCEPTION_PARAMETER));
        if (throwExceptionParameter) {
            throw new PortletException("(" + this.getFormattedDate() + ") Throwing exception because of RenderRequest parameter. A Refresh should clear this error message.");
        }
    }

    protected String getFormattedDate() {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        return dateFormat.format(new Date());
    }
}
