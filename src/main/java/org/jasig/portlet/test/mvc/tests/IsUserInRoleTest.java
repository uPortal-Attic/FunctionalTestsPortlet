/**
 * Copyright 2007 The JA-SIG Collaborative.  All rights reserved.
 * See license distributed with this file and
 * available online at http://www.uportal.org/license.html
 */
package org.jasig.portlet.test.mvc.tests;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class IsUserInRoleTest extends AbstractController {
    private Map<String, String> roles = null;
    
    /**
     * @return the roles
     */
    public Map<String, String> getRoles() {
        return this.roles;
    }
    /**
     * @param roles the roles to set
     */
    @Required
    public void setRoles(Map<String, String> roles) {
        Validate.notNull(roles);
        this.roles = roles;
    }


    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleRenderRequestInternal(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @Override
    protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
        final Map<String, Boolean> roleTests = new HashMap<String, Boolean>();
        final Map<String, Boolean> roleLinkTests = new HashMap<String, Boolean>();
        
        for (final Map.Entry<String, String> roleEntry : this.roles.entrySet()) {
            final String role = roleEntry.getKey();
            final boolean userInRole = request.isUserInRole(role);
            roleTests.put(role, userInRole);
            
            final String roleLink = roleEntry.getValue();
            final boolean userInRoleLink = request.isUserInRole(roleLink);
            roleLinkTests.put(roleLink, userInRoleLink);
        }
        
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("roles", this.roles);
        model.put("roleTests", roleTests);
        model.put("roleLinkTests", roleLinkTests);
        
        return new ModelAndView("userInRoleTest", model);
    }
}
