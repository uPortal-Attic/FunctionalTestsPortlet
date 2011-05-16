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

package org.jasig.portlet.test.web;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.portlet.ParameterAddingActionRequestWrapper;
import org.springframework.portlet.ParameterAddingEventRequestWrapper;
import org.springframework.portlet.ParameterAddingPortletRequestWrapper;
import org.springframework.portlet.ParameterAddingRenderRequestWrapper;
import org.springframework.portlet.ParameterAddingResourceRequestWrapper;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
@Component("testSelectingFilter")
public class TestNameParameterFilter implements ActionFilter, EventFilter, RenderFilter, ResourceFilter  {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    private String currentTestAttributeName = "currentTest";
    private String currentTestParameterName = "currentTest";
    
    
    public void setCurrentTestAttributeName(String currentTestAttributeName) {
        this.currentTestAttributeName = currentTestAttributeName;
    }

    public void setCurrentTestParameterName(String currentTestParameterName) {
        this.currentTestParameterName = currentTestParameterName;
    }

    @Override
    public void init(FilterConfig filterConfig) throws PortletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ResourceRequest request, ResourceResponse response, FilterChain chain) throws IOException,
            PortletException {
        
        request = this.doFilterInternal(request, new Function<ResourceRequest, ParameterAddingResourceRequestWrapper>() {
            @Override
            public ParameterAddingResourceRequestWrapper apply(ResourceRequest input) {
                return new ParameterAddingResourceRequestWrapper(input);
            }
        });
        
        chain.doFilter(request, response);
    }

    @Override
    public void doFilter(RenderRequest request, RenderResponse response, FilterChain chain) throws IOException,
            PortletException {
        
        request = this.doFilterInternal(request, new Function<RenderRequest, ParameterAddingRenderRequestWrapper>() {
            @Override
            public ParameterAddingRenderRequestWrapper apply(RenderRequest input) {
                return new ParameterAddingRenderRequestWrapper(input);
            }
        });
        
        chain.doFilter(request, response);
    }

    @Override
    public void doFilter(EventRequest request, EventResponse response, FilterChain chain) throws IOException,
            PortletException {
        
        request = this.doFilterInternal(request, new Function<EventRequest, ParameterAddingEventRequestWrapper>() {
            @Override
            public ParameterAddingEventRequestWrapper apply(EventRequest input) {
                return new ParameterAddingEventRequestWrapper(input);
            }
        });
        
        chain.doFilter(request, response);
    }

    @Override
    public void doFilter(ActionRequest request, ActionResponse response, FilterChain chain) throws IOException,
            PortletException {
        
        request = this.doFilterInternal(request, new Function<ActionRequest, ParameterAddingActionRequestWrapper>() {
            @Override
            public ParameterAddingActionRequestWrapper apply(ActionRequest input) {
                return new ParameterAddingActionRequestWrapper(input);
            }
        });
        
        chain.doFilter(request, response);
    }
    
    @SuppressWarnings("unchecked")
    protected <R extends PortletRequest, W extends ParameterAddingPortletRequestWrapper> R doFilterInternal(R request, Function<R, W> createWrapper) {
        final PortletSession portletSession = request.getPortletSession();
        
        String currentTest = request.getParameter(currentTestParameterName);
        if (currentTest != null) {
            logger.debug("Found test {} as request parameter {}, storing in session under {}", new Object[] { currentTest, currentTestParameterName, currentTestAttributeName });
            portletSession.setAttribute(currentTestAttributeName, currentTest);
        }
        else {
            currentTest = (String)portletSession.getAttribute(currentTestParameterName);
            
            if (currentTest != null) {
                logger.debug("Found test {} as session attribute {}, adding to request as private attribute {}", new Object[] { currentTest, currentTestAttributeName, currentTestParameterName });
                
                final W requestWrapper = createWrapper.apply(request);
                requestWrapper.putPrivateParameters(currentTestParameterName, new String[] { currentTest });
                request = (R)requestWrapper;
            }
        }
        
        return request;
    }
}
