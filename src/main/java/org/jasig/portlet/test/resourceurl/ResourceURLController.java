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
package org.jasig.portlet.test.resourceurl;

import java.util.Date;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * Simple {@link Controller} that responds to test resource urls.
 * 
 * @author Nicholas Blair
 * @version $Id$
 */
@Controller
@RequestMapping("VIEW")
public class ResourceURLController {

	@RequestMapping
	protected String displayHomeView() {
		return "resourceUrlTest";
	}
	
	@ResourceMapping("jsonResourceUrl")
	protected View handleJsonResourceUrl(ResourceRequest request, ResourceResponse response, ModelMap model) {
		model.addAttribute("hello", "world");
		model.addAttribute("currentTime", new Date());
		return new MappingJacksonJsonView();
	}
}
