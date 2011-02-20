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
package org.jasig.portlet.test.cookie;

import javax.servlet.http.Cookie;

/**
 * Java bean that wraps a {@link Cookie} object.
 * The primary purpose for this class is due to {@link Cookie}'s
 * use of "getSecure" instead of "isSecure".
 * 
 * @author Nicholas Blair
 * @version $Id$
 */
public class CookieBeanWrapper {

	private final Cookie cookie;

	/**
	 * @param cookie
	 */
	public CookieBeanWrapper(Cookie cookie) {
		this.cookie = cookie;
	}

	/**
	 * @return
	 * @see javax.servlet.http.Cookie#getComment()
	 */
	public String getComment() {
		return cookie.getComment();
	}

	/**
	 * @return
	 * @see javax.servlet.http.Cookie#getDomain()
	 */
	public String getDomain() {
		return cookie.getDomain();
	}

	/**
	 * @return
	 * @see javax.servlet.http.Cookie#getMaxAge()
	 */
	public int getMaxAge() {
		return cookie.getMaxAge();
	}

	/**
	 * @return
	 * @see javax.servlet.http.Cookie#getName()
	 */
	public String getName() {
		return cookie.getName();
	}

	/**
	 * @return
	 * @see javax.servlet.http.Cookie#getPath()
	 */
	public String getPath() {
		return cookie.getPath();
	}

	/**
	 * @return
	 * @see javax.servlet.http.Cookie#getSecure()
	 */
	public boolean isSecure() {
		return cookie.getSecure();
	}

	/**
	 * @return
	 * @see javax.servlet.http.Cookie#getValue()
	 */
	public String getValue() {
		return cookie.getValue();
	}

	/**
	 * @return
	 * @see javax.servlet.http.Cookie#getVersion()
	 */
	public int getVersion() {
		return cookie.getVersion();
	}

	/**
	 * @param purpose
	 * @see javax.servlet.http.Cookie#setComment(java.lang.String)
	 */
	public void setComment(String purpose) {
		cookie.setComment(purpose);
	}

	/**
	 * @param pattern
	 * @see javax.servlet.http.Cookie#setDomain(java.lang.String)
	 */
	public void setDomain(String pattern) {
		cookie.setDomain(pattern);
	}

	/**
	 * @param expiry
	 * @see javax.servlet.http.Cookie#setMaxAge(int)
	 */
	public void setMaxAge(int expiry) {
		cookie.setMaxAge(expiry);
	}

	/**
	 * @param uri
	 * @see javax.servlet.http.Cookie#setPath(java.lang.String)
	 */
	public void setPath(String uri) {
		cookie.setPath(uri);
	}

	/**
	 * @param flag
	 * @see javax.servlet.http.Cookie#setSecure(boolean)
	 */
	public void setSecure(boolean flag) {
		cookie.setSecure(flag);
	}

	/**
	 * @param newValue
	 * @see javax.servlet.http.Cookie#setValue(java.lang.String)
	 */
	public void setValue(String newValue) {
		cookie.setValue(newValue);
	}

	/**
	 * @param v
	 * @see javax.servlet.http.Cookie#setVersion(int)
	 */
	public void setVersion(int v) {
		cookie.setVersion(v);
	}
	
}
