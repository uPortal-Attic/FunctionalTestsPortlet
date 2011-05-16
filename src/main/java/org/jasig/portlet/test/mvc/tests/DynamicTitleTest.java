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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
@Controller("dynamicTitleTest")
@RequestMapping(value = {"VIEW", "EDIT", "HELP", "ABOUT"}, params="currentTest=dynamicTitleTest")
public class DynamicTitleTest  extends BasePortletTest {

    private static final String ACTION_TITLE = "ACTION_TITLE";
    private static final String RENDER_TITLE = "RENDER_TITLE";
    private static final String DATE_FORMAT = "yyyy-MM-dd - HH:mm:ss.SSS";
    
    @Override
    public String getTestName() {
        return "Dynamic Title Test";
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleActionRequestInternal(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    @ActionMapping
    public void handleActionRequestInternal(ActionRequest request, ActionResponse response) throws Exception {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        final String formattedDate = simpleDateFormat.format(new Date());
        response.setRenderParameter(ACTION_TITLE, "Action '" + formattedDate + "'");
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleRenderRequestInternal(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @RenderMapping
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
