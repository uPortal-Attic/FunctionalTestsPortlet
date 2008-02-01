/**
 * Copyright 2007 The JA-SIG Collaborative.  All rights reserved.
 * See license distributed with this file and
 * available online at http://www.uportal.org/license.html
 */
package org.jasig.portlet.test.mvc.tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class DynamicTitleTest extends AbstractController {
    private static final String ACTION_TITLE = "ACTION_TITLE";
    private static final String RENDER_TITLE = "RENDER_TITLE";
    private static final String DATE_FORMAT = "yyyy-MM-dd - HH:mm:ss.SSS";

    public DynamicTitleTest() {
        this.setRenderWhenMinimized(true);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleActionRequestInternal(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    @Override
    protected void handleActionRequestInternal(ActionRequest request, ActionResponse response) throws Exception {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        final String formattedDate = simpleDateFormat.format(new Date());
        response.setRenderParameter(ACTION_TITLE, "Action '" + formattedDate + "'");
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleRenderRequestInternal(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @Override
    protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
        final String actionTitle = request.getParameter(ACTION_TITLE);
        
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        final String formattedDate = simpleDateFormat.format(new Date());
        final String renderTitle = "Render '" + formattedDate + "'";
        
        if (actionTitle != null) {
            response.setTitle(actionTitle);
        }
        else {
            response.setTitle(renderTitle);
        }
        
        final Map<String, String> model = new HashMap<String, String>();
        model.put(ACTION_TITLE, actionTitle);
        model.put(RENDER_TITLE, renderTitle);
        
        return new ModelAndView("dynamicTitleTest", model);
    }
}
