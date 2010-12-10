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
package org.jasig.portlet.test.header;

import java.io.IOException;

import javax.portlet.GenericPortlet;
import javax.portlet.PortalContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Subclass of {@link GenericPortlet} that simulates a portlet that
 * provides output for the "head" (as well as the body).
 * 
 * Spring 3 currently doesn't provide support for the doHeaders method.
 * 
 * @author Nicholas Blair
 * @version $Id$
 */
public class HeaderTestPortlet extends GenericPortlet {

	private Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Checks for the correct value of {@link PortletRequest#RENDER_PART} (expects {@link PortletRequest#RENDER_HEADERS}.
	 * When correct, prints a style sheet using the color green; color red for incorrect value.
	 * 
	 * @see javax.portlet.GenericPortlet#doHeaders(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	@Override
	protected void doHeaders(RenderRequest request, RenderResponse response) {
		final String renderPart = (String) request.getAttribute(PortletRequest.RENDER_PART);
		String color = "red";
		if(PortletRequest.RENDER_HEADERS.equals(renderPart)) {
			color = "green";
		}
		try {
			response.getWriter().write("<style type=\"text/css\">#headerTestCustomText { color:" + color + "; }</style>");
		} catch (IOException e) {
			logger.error("caught IOException", e);
		}
	}

	/**
	 * <ol>
	 * <li>Checks for correct value of {@link PortalContext.MARKUP_HEAD_ELEMENT_SUPPORT}</li>
	 * <li>Checks for correct value of {@link PortletRequest.RENDER_PART}</li>
	 * <li>Prints sample text that should display using the style provided by the {@link #doHeaders(RenderRequest, RenderResponse)} implementation</li>
	 * </ol>
	 * @see javax.portlet.GenericPortlet#doView(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		PortalContext portalContext = request.getPortalContext();
		String headElementSupportProperty = portalContext.getProperty(PortalContext.MARKUP_HEAD_ELEMENT_SUPPORT);
		boolean headElementSupport = (headElementSupportProperty != null);
		final String renderPart = (String) request.getAttribute(PortletRequest.RENDER_PART);
		boolean renderPartCorrect = PortletRequest.RENDER_MARKUP.equals(renderPart);
		
		StringBuilder viewOutput = new StringBuilder();
		viewOutput.append("<ul><li>Does this portal correctly advertise support for head output (javax.portlet.PortalContext#MARKUP_HEAD_ELEMENT_SUPPORT)? <span class=\"headerTestResult\">");
		viewOutput.append(headElementSupport);
		viewOutput.append("</li><li>Does this portal correctly set the value for for javax.portlet.PortletRequest#RENDER_PART when rendering the portlet body markup? <span class=\"headerTestResult\">");
		viewOutput.append(renderPartCorrect);
		viewOutput.append("</li><li>If the header was properly rendered, the following text should be GREEN: <span id=\"headerTestCustomText\" class=\"headerTestResult\">Hello World!</span></li></ul>");
		try {
			response.getWriter().write(viewOutput.toString());
		} catch (IOException e) {
			logger.error("caught IOException", e);
		}
	}

}
