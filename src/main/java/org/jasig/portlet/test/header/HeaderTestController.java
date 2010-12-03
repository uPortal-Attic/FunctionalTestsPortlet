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

import javax.portlet.PortalContext;
import javax.portlet.PortletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@link Controller} for testing portlet head output support.
 *  
 * @author Nicholas Blair
 * @version $Id$
 */
@Controller
@RequestMapping("VIEW")
public class HeaderTestController {

	/**
	 * 
	 * @param renderRequest
	 * @return
	 */
	@RequestMapping()
	protected String onRenderRequest(PortletRequest renderRequest, ModelMap model) {
		PortalContext portalContext = renderRequest.getPortalContext();
		String headElementSupport = portalContext.getProperty(PortalContext.MARKUP_HEAD_ELEMENT_SUPPORT);
		if(headElementSupport != null) {
			model.addAttribute("headElementSupportProperlyAdvertised", true);
		} else {
			model.addAttribute("headElementSupportProperlyAdvertised", false);
		}
		
		final String renderPart = (String) renderRequest.getAttribute(PortletRequest.RENDER_PART);
		
		if(PortletRequest.RENDER_HEADERS.equals(renderPart)) {
			// short circuit and render the head output
			return "headerTestCss";
		} 
		
		model.addAttribute("portletRequestRenderPartValueForBody", PortletRequest.RENDER_MARKUP.equals(renderPart));
		return "headerTestView";
	}
}
