/**
 * Copyright 2007 The JA-SIG Collaborative.  All rights reserved.
 * See license distributed with this file and
 * available online at http://www.uportal.org/license.html
 */
package org.jasig.portlet.test.mvc.tests;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.collections15.EnumerationUtils;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class LocaleTest extends AbstractController {
    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleRenderRequestInternal(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @Override
    protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
        final Locale preferedRequestLocale = request.getLocale();
        final Enumeration<Locale> requestLocales = request.getLocales();
        final Locale responseLocale = response.getLocale();

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("preferedRequestLocale", preferedRequestLocale);
        model.put("requestLocales", EnumerationUtils.toList(requestLocales));
        model.put("responseLocale", responseLocale);
        
        return new ModelAndView("localeTest", model);
    }
}
