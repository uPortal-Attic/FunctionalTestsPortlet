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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
@Controller("userInRoleTest")
@RequestMapping(value = {"VIEW", "EDIT", "HELP", "ABOUT"}, params="currentTest=userInRoleTest")
public class IsUserInRoleTest extends BasePortletTest {

    @Override
    public String getTestName() {
        return "User Roles Test";
    }

    private Map<String, String> roles = null;
    
    @Resource(name="portletRoles")
    public void setRoles(Map<String, String> roles) {
        Validate.notNull(roles);
        this.roles = roles;
    }


    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleRenderRequestInternal(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @RenderMapping
    public ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
        final Map<String, Boolean> roleTests = new HashMap<String, Boolean>();
        final Map<String, Boolean> roleLinkTests = new HashMap<String, Boolean>();
        
        for (final Map.Entry<String, String> roleEntry : this.roles.entrySet()) {
            final String role = roleEntry.getKey();
            final boolean userInRole = request.isUserInRole(role);
            roleTests.put(role, userInRole);
            
            final String roleLink = roleEntry.getValue();
            if (!StringUtils.isEmpty(roleLink)) {
                final boolean userInRoleLink = request.isUserInRole(roleLink);
                roleLinkTests.put(roleLink, userInRoleLink);
            }
        }
        
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("roles", this.roles);
        model.put("roleTests", roleTests);
        model.put("roleLinkTests", roleLinkTests);
        
        return new ModelAndView("userInRoleTest", model);
    }
    
    @ActionMapping
    public void noopAction() {
    }
}
