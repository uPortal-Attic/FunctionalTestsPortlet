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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
@Controller("userInfoTest")
@RequestMapping(value = {"VIEW", "EDIT", "HELP", "ABOUT"}, params="currentTest=userInfoTest")
public class GetExpectedUserAttributesTest extends BasePortletTest {
    private String USER_INFO_MULTIVALUED = "org.jasig.portlet.USER_INFO_MULTIVALUED";

    @Override
    public String getTestName() {
        return "User Info Test";
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleActionRequestInternal(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    @ActionMapping
    public void handleActionRequestInternal(ActionRequest request, ActionResponse response) throws Exception {
        final List<String> attributesNames = Collections.list(request.getAttributeNames());
    	final Map<String, String> userInfo = (Map<String, String>)request.getAttribute(PortletRequest.USER_INFO);
    	final Map<String, List<Object>> multivaluedUserInfo = (Map<String, List<Object>>)request.getAttribute(USER_INFO_MULTIVALUED);
    	
        response.setRenderParameter("AttributeNames", String.valueOf(attributesNames));
        response.setRenderParameter(PortletRequest.USER_INFO, String.valueOf(userInfo));
        response.setRenderParameter(USER_INFO_MULTIVALUED, String.valueOf(multivaluedUserInfo));
    }
    
    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleRenderRequestInternal(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @RenderMapping
    public ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
        final List<String> attributesNames = Collections.list(request.getAttributeNames());
        final Map<String, String> userInfo = (Map<String, String>)request.getAttribute(PortletRequest.USER_INFO);
        final Map<String, List<Object>> multivaluedUserInfo = (Map<String, List<Object>>)request.getAttribute(USER_INFO_MULTIVALUED);
        
        final Map<String, String> model = new LinkedHashMap<String, String>();
        model.put("ACTION_AttributeNames", request.getParameter("AttributeNames"));
        model.put("ACTION_USER_INFO", request.getParameter(PortletRequest.USER_INFO));
        model.put("ACTION_USER_INFO_MULTIVALUED", request.getParameter(USER_INFO_MULTIVALUED));
        
        model.put("RENDER_AttributeNames", String.valueOf(attributesNames));
        model.put("RENDER_USER_INFO", String.valueOf(userInfo));
        model.put("RENDER_USER_INFO_MULTIVALUED", String.valueOf(multivaluedUserInfo));
        
        return new ModelAndView("getExpectedUserAttributesTest", model);
    }
    
}
