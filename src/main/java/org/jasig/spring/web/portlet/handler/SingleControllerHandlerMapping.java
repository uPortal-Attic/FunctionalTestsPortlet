/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.spring.web.portlet.handler;

import javax.portlet.PortletRequest;

import org.springframework.web.portlet.handler.AbstractHandlerMapping;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class SingleControllerHandlerMapping extends AbstractHandlerMapping {
    /* (non-Javadoc)
     * @see org.springframework.web.portlet.handler.AbstractHandlerMapping#getHandlerInternal(javax.portlet.PortletRequest)
     */
    @Override
    protected Object getHandlerInternal(PortletRequest request) throws Exception {
        return null;
    }

}
