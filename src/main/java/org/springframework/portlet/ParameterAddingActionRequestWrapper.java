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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.portlet.ActionRequest;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class ParameterAddingActionRequestWrapper extends ParameterAddingPortletRequestWrapper implements ActionRequest {
    private final ActionRequest request;
    
    public ParameterAddingActionRequestWrapper(ActionRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public InputStream getPortletInputStream() throws IOException {
        return this.request.getPortletInputStream();
    }

    @Override
    public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
        this.request.setCharacterEncoding(enc);
    }

    @Override
    public BufferedReader getReader() throws UnsupportedEncodingException, IOException {
        return this.request.getReader();
    }

    @Override
    public String getCharacterEncoding() {
        return this.request.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
        return this.request.getContentType();
    }

    @Override
    public int getContentLength() {
        return this.request.getContentLength();
    }

    @Override
    public String getMethod() {
        return this.request.getMethod();
    }
}
