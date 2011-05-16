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

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.jasig.portlet.test.mvc.tests.PortletTest;
import org.jasig.portlet.test.mvc.tests.PortletTestComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

/**
 * Add the set of available tests to the model
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public class TestPickerInterceptor extends HandlerInterceptorAdapter {
    private Set<PortletTest> portletTests = Collections.emptySet();

    @Autowired
    public void setPortletTests(Collection<PortletTest> portletTests) {
        final Set<PortletTest> newPortletTests = new TreeSet<PortletTest>(PortletTestComparator.INSTANCE);
        newPortletTests.addAll(portletTests);
        
        this.portletTests = newPortletTests;
    }

    @Override
    public void postHandleRender(RenderRequest request, RenderResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("portletTests", portletTests);
        }
    }
}
