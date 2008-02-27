/**
 * Copyright 2007 The JA-SIG Collaborative.  All rights reserved.
 * See license distributed with this file and
 * available online at http://www.uportal.org/license.html
 */
package org.jasig.portlet.test.mvc.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.list.LazyList;
import org.jasig.portlet.test.om.prefs.Preference;
import org.jasig.portlet.test.om.prefs.Preferences;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestUtils;
import org.springframework.web.portlet.mvc.AbstractFormController;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class PortletPrefrencesTest extends AbstractFormController {
    private static final PreferenceFactory PREFERENCE_FACTORY = new PreferenceFactory();
    private static final StringFactory STRING_FACTORY = new StringFactory();
    
    private static final class PreferenceFactory implements Factory<Preference> {
        public Preference create() {
            final Preference preference = new Preference();
            
            List<String> values = new LinkedList<String>();
            values = LazyList.decorate(values, STRING_FACTORY);
            preference.setValues(values);
            
            return preference;
        }
    }
    
    private static final class StringFactory implements Factory<String> {
        public String create() {
            return new String();
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractFormController#processFormSubmission(javax.portlet.ActionRequest, javax.portlet.ActionResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void processFormSubmission(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
        final Preferences preferences = (Preferences)command;
        final List<Preference> preferencesList = preferences.getPreferences();
        
        //Remove any preferences marked for removal
        final int delPrefIndex = PortletRequestUtils.getIntParameter(request, "delPrefIndex", -1);
        if (delPrefIndex >= 0) {
            final int delValueIndex = PortletRequestUtils.getIntParameter(request, "delValueIndex", -1);
            if (delValueIndex >= 0) {
                final Preference preference = preferencesList.get(delPrefIndex);
                final List<String> values = preference.getValues();
                values.remove(delValueIndex);
            }
            else {
                preferencesList.remove(delPrefIndex);
            }
        }
        
        final PortletPreferences portletPreferences = request.getPreferences();
        
        //Clear out all preferences before adding new ones
        for (final Enumeration<String> names = portletPreferences.getNames(); names.hasMoreElements(); ) {
            final String name = names.nextElement();
            portletPreferences.reset(name);
        }
        
        //Add all the submitted preferences to the PortletPreferences
        for (final Preference preference : preferencesList) {
            final String name = preference.getName();
            final List<String> values = preference.getValues();
            final String[] valuesArray;
            if (values != null) {
                valuesArray = values.toArray(new String[values.size()]);
            }
            else {
                valuesArray = null;
            }
            
            portletPreferences.setValues(name, valuesArray);
        }
        
        //Persist the changes
        portletPreferences.store();
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractFormController#renderFormSubmission(javax.portlet.RenderRequest, javax.portlet.RenderResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    @Override
    protected ModelAndView renderFormSubmission(RenderRequest request, RenderResponse response, Object command, BindException errors) throws Exception {
        //Always do a clean re-render
        return super.showNewForm(request, response);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractFormController#showForm(javax.portlet.RenderRequest, javax.portlet.RenderResponse, org.springframework.validation.BindException)
     */
    @Override
    protected ModelAndView showForm(RenderRequest request, RenderResponse response, BindException errors) throws Exception {
        return super.showForm(request, errors, "portletPreferencesTest");
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractFormController#formBackingObject(javax.portlet.PortletRequest)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Object formBackingObject(PortletRequest request) throws Exception {
        final boolean formSubmission = isFormSubmission(request);
        
        final PortletPreferences portletPreferences = request.getPreferences();
        final Map<String, String[]> prefMap = portletPreferences.getMap();
        
        List<Preference> preferences = new ArrayList<Preference>(prefMap.size() + 1);
        if (formSubmission) {
            preferences = LazyList.decorate(preferences, PREFERENCE_FACTORY);
        }

        for (final Map.Entry<String, String[]> prefEntry : prefMap.entrySet()) {
            final Preference preference = new Preference();
            preference.setName(prefEntry.getKey());

            final String[] values = prefEntry.getValue();
            
            List<String> valuesList = new ArrayList<String>(Arrays.asList(values));
            if (formSubmission) {
                valuesList = LazyList.decorate(valuesList, STRING_FACTORY);
            }
            
            preference.setValues(valuesList);
            preferences.add(preference);
        }
        
        final Preferences prefs = new Preferences();
        prefs.setPreferences(preferences);
        return prefs;
    }
}
