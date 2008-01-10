/**
 * Copyright 2007 The JA-SIG Collaborative.  All rights reserved.
 * See license distributed with this file and
 * available online at http://www.uportal.org/license.html
 */
package org.jasig.portlet.test.mvc.tests;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class StateModeTest extends AbstractController {
    public StateModeTest() {
        this.setRenderWhenMinimized(true);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleActionRequestInternal(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    @Override
    protected void handleActionRequestInternal(ActionRequest request, ActionResponse response) throws Exception {
        final PortletMode actionPortletMode = request.getPortletMode();
        final WindowState actionWindowState = request.getWindowState();

        this.logger.info("Handling ActionRequest with PortletMode='" + actionPortletMode + "' and WindowState='" + actionWindowState + "'");
        
        response.setRenderParameter("ACTION_PORTLET_MODE", actionPortletMode.toString());
        response.setRenderParameter("ACTION_WINDOW_STATE", actionWindowState.toString());
        
        
        final String requestedPortletMode = request.getParameter("REQUESTED_PORTLET_MODE");
        if (requestedPortletMode != null) {
            this.logger.info("PortletMode '" + requestedPortletMode + "' requested to be set during action.");
            final PortletMode portletMode = new PortletMode(requestedPortletMode);
            
            final boolean portletModeAllowed = request.isPortletModeAllowed(portletMode);
            if (portletModeAllowed) {
                try {
                    response.setPortletMode(portletMode);
                }
                catch (PortletException pe) {
                    this.logger.warn("Failed to set requested PortletMode to '" + portletMode + "' during action.", pe);
                    throw pe;
                }
            }
            else {
                response.setRenderParameter("REQUESTED_PORTLET_MODE_NOT_ALLOWED", portletMode.toString());
                this.logger.info("PortletMode '" + portletMode + "' is not allowed.");
            }
        }
        
        final String requestedWindowState = request.getParameter("REQUESTED_WINDOW_STATE");
        if (requestedWindowState != null) {
            this.logger.info("WindowState '" + requestedWindowState + "' requested to be set during action.");
            final WindowState windowState = new WindowState(requestedWindowState);
            
            final boolean windowStateAllowed = request.isWindowStateAllowed(windowState);
            if (windowStateAllowed) {
                try {
                    response.setWindowState(windowState);
                }
                catch (PortletException pe) {
                    this.logger.warn("Failed to set requested WindowState to '" + windowState + "' during action.", pe);
                    throw pe;
                }
            }
            else {
                response.setRenderParameter("REQUESTED_WINDOW_STATE_NOT_ALLOWED", windowState.toString());
                this.logger.info("WindowState '" + windowState + "' is not allowed.");
            }
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleRenderRequestInternal(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @Override
    protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
        final PortalContext portalContext = request.getPortalContext();
        
        final List<String> supportedPortletModes = this.toStringList(portalContext.getSupportedPortletModes());
        final List<String> supportedWindowStates = this.toStringList(portalContext.getSupportedWindowStates());
        
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("supportedPortletModes", supportedPortletModes);
        model.put("supportedWindowStates", supportedWindowStates);
        
        return new ModelAndView("stateModeTest", model);
    }
    
    protected List<String> toStringList(Enumeration<?> enumeration) {
        final List<String> list = new LinkedList<String>();
        
        while (enumeration.hasMoreElements()) {
            final Object nextElement = enumeration.nextElement();
            list.add(nextElement.toString());
        }
        
        return list;
    }
}
