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
package org.jasig.portlet.test.mvc.tests;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.servlet.http.Cookie;
import javax.validation.Valid;

import org.apache.commons.lang.RandomStringUtils;
import org.jasig.portlet.test.cookie.CookieBeanWrapper;
import org.jasig.portlet.test.cookie.CreateCookieFormBackingObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * {@link Controller} for testing Portlet Cookie support.
 * 
 * @author Nicholas Blair
 * @version $Id$
 */
@Controller("cookieTest")
@RequestMapping(value = {"VIEW", "EDIT", "HELP", "ABOUT"}, params="currentTest=cookieTest")
@SessionAttributes("command")
public class CookieTestController extends BasePortletTest {

    @Override
    public String getTestName() {
        return "Cookie Test";
    }


    /**
	 * Sets up a {@link Validator} for the forms in this {@link Controller}.
	 * @param binder
	 */
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new Validator() {
			@Override
			public boolean supports(Class<?> clazz) {
				return CreateCookieFormBackingObject.class.equals(clazz);
			}
			@Override
			public void validate(Object target, Errors errors) {
				ValidationUtils.rejectIfEmpty(errors, "name", "name.empty", "name field cannot be empty");
				ValidationUtils.rejectIfEmpty(errors, "value", "value.empty", "value field cannot be empty");
			}
        });
    }

	
	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RenderMapping
	protected String displayCookies(ModelMap model, RenderRequest request) {
		List<CookieBeanWrapper> cookieList = new ArrayList<CookieBeanWrapper>();
		Cookie [] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie: cookies) {
				cookieList.add(new CookieBeanWrapper(cookie));
			}
		}
		model.addAttribute("cookieListSize", cookieList.size());
		model.addAttribute("cookieList", cookieList);
		CreateCookieFormBackingObject command = new CreateCookieFormBackingObject();
		model.addAttribute("command", command);
		return "cookieTestView";
	}
	
	@ActionMapping
    public void noopAction() {
    }

    /**
	 * Generates a new cookie with random name and value.
	 * 
	 * @param response
	 */
	@ActionMapping(value="randomCookieAction")
	protected void createRandomCookie(ActionRequest request, ActionResponse response) {
		final String name = RandomStringUtils.randomAlphabetic(8);
		final String value = RandomStringUtils.randomAlphanumeric(8);
		Cookie cookie = new Cookie(name, value);
		cookie.setComment("Random Cookie Test comment");
		cookie.setMaxAge(-1);
		cookie.setSecure(request.isSecure());
		response.addProperty(cookie);
		
	}
	
	/**
	 * Creates a cookie from the form.
	 * 
	 * @param command
	 * @param errors
	 * @param response
	 */
	@ActionMapping(value="formCookieAction")
	protected void createFormCookie(@ModelAttribute @Valid CreateCookieFormBackingObject command, BindingResult errors, ActionRequest request, ActionResponse response) {
		if(errors.hasErrors()) {
			return;
		}
		
		Cookie cookie = command.toCookie();
		
		response.addProperty(cookie);
	}
	
	/**
	 * Alter an existing cookie that has the same name as the command object.
	 * Ignores invocations where the command does not match an existing cookie (by name).
	 * 
	 * @param command
	 * @param errors
	 * @param request
	 * @param response
	 */
	@ActionMapping(value="editCookieAction")
	protected void editExistingCookie(CreateCookieFormBackingObject command, ActionRequest request, ActionResponse response) {
		if(command == null) {
			return;
		}
		
		Cookie [] existingCookies = request.getCookies();
		if(existingCookies != null) {
			Cookie cookieToAlter = null;
			for(Cookie existing: existingCookies) {
				if(existing.getName().equals(command.getName())) {
					cookieToAlter = existing;
					break;
				}
			}
			
			if(cookieToAlter != null) {
				response.addProperty(command.toCookie());
			}
		}
	}
}
