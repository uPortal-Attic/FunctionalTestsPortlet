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
package org.springframework.portlet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.filter.PortletRequestWrapper;

import org.apache.commons.lang.ArrayUtils;

/**
 * Adds parameters to a portlet request
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public class ParameterAddingPortletRequestWrapper extends PortletRequestWrapper {
    private final Map<String, String[]> additionalPrivateParameters = new LinkedHashMap<String, String[]>();
    private final Map<String, String[]> additionalPublicParameters = new LinkedHashMap<String, String[]>();

    public ParameterAddingPortletRequestWrapper(PortletRequest request) {
        super(request);
    }
    
    public String[] putPublicParameters(String name, String[] values) {
        return this.additionalPublicParameters.put(name, values);
    }
    public String[] putPrivateParameters(String name, String[] values) {
        return this.additionalPrivateParameters.put(name, values);
    }
    public void putPublicAllParameter(Map<String, String[]> params) {
        this.additionalPublicParameters.putAll(params);
    }
    public void putPrivateAllParameter(Map<String, String[]> params) {
        this.additionalPrivateParameters.putAll(params);
    }

    @Override
    public String getParameter(String name) {
        final String[] values = this.getParameterValues(name);
        if (ArrayUtils.isNotEmpty(values)) {
            return values[0];
        }
        
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        final Map<String, String[]> parameterMap = new LinkedHashMap<String, String[]>();
        
        parameterMap.putAll(super.getParameterMap());
        parameterMap.putAll(this.additionalPublicParameters);
        parameterMap.putAll(this.additionalPrivateParameters);
        
        return Collections.unmodifiableMap(parameterMap);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        final Map<String, String[]> parameterMap = this.getParameterMap();
        return Collections.enumeration(parameterMap.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        if (this.additionalPrivateParameters.containsKey(name)) {
            return this.additionalPrivateParameters.get(name);
        }
        
        if (this.additionalPublicParameters.containsKey(name)) {
            return this.additionalPublicParameters.get(name);
        }
        
        return super.getParameterValues(name);
    }

    @Override
    public Map<String, String[]> getPrivateParameterMap() {
final Map<String, String[]> parameterMap = new LinkedHashMap<String, String[]>();
        
        parameterMap.putAll(super.getParameterMap());
        parameterMap.putAll(this.additionalPrivateParameters);
        
        return Collections.unmodifiableMap(parameterMap);
    }

    @Override
    public Map<String, String[]> getPublicParameterMap() {
        final Map<String, String[]> parameterMap = new LinkedHashMap<String, String[]>();
        
        parameterMap.putAll(super.getParameterMap());
        parameterMap.putAll(this.additionalPublicParameters);
        
        return Collections.unmodifiableMap(parameterMap);
    }
}
