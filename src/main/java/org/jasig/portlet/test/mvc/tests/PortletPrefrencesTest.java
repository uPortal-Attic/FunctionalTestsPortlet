/**
 * Copyright 2007 The JA-SIG Collaborative.  All rights reserved.
 * See license distributed with this file and
 * available online at http://www.uportal.org/license.html
 */
package org.jasig.portlet.test.mvc.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.ValidatorException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
@Controller("preferencesTest")
@RequestMapping(value = {"VIEW", "EDIT", "HELP", "ABOUT"}, params="currentTest=preferencesTest")
public class PortletPrefrencesTest extends BasePortletTest {
    private static final String NULL_PREFERENCES = "NULL_PREFERENCES";
    private static final String MIXED_PREFERENCES = "MIXED_PREFERENCES";
    private static final String NULL_PREFERENCE = "NULL_PREFERENCE";
    private static final String EMPTY_PREFERENCE = "EMPTY_PREFERENCE";

    @Override
    public String getTestName() {
        return "Preferences Test";
    }
    
    @ActionMapping
    public void noopAction() {
    }
    
    @ActionMapping(value="setEmptyAndNull")
    public void setEmptyAndNullPrefernces(PortletRequest request) throws ReadOnlyException, ValidatorException, IOException {
        final PortletPreferences preferences = request.getPreferences();
        preferences.setValue(EMPTY_PREFERENCE, "");
        preferences.setValue(NULL_PREFERENCE, null);
        preferences.setValues(MIXED_PREFERENCES, new String[] {null, "", null, "", null});
        preferences.setValues(NULL_PREFERENCES, null);
        preferences.store();
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleRenderRequestInternal(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @RenderMapping
    protected ModelAndView handleRenderRequestInternal(RenderRequest request) throws Exception {
        final PortletPreferences preferences = request.getPreferences();

        final Map<String, Object> model = new HashMap<String, Object>();

        testPreference(preferences, model, EMPTY_PREFERENCE, "", new String[] {""});
        testPreference(preferences, model, NULL_PREFERENCE, null, new String[] {null});
        testPreference(preferences, model, MIXED_PREFERENCES, "", new String[] {null, "", null, "", null});
        testPreference(preferences, model, NULL_PREFERENCES, null, null);
        
        return new ModelAndView("preferencesTest", model);
    }
    
    protected void testPreference(PortletPreferences preferences, Map<String, Object> model, String pref, String expectedValue, String[] expectedValues) {
        final String value = preferences.getValue(pref, "DEFAULT");
        final String[] values = preferences.getValues(pref, new String[] {"DEFAULT"});
        model.put(pref + "_value", expectedValue == value || (expectedValue != null && expectedValue.equals(value)));
        model.put(pref + "_values", Arrays.equals(expectedValues, values));
    }
}
