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
package org.jasig.portlet.test.mvc.tests;

import javax.portlet.CacheControl;

/**
 * @author Nicholas Blair
 * @version $Id$
 */
public class CacheControlFormBackingObject {

	private String etag;
	private boolean useCachedContent = false;
	private int expirationTime = 0;
	private boolean usePublicScope = false;
	
	public CacheControlFormBackingObject() {
	}
	public CacheControlFormBackingObject(CacheControl cacheControl) {
		this.etag = cacheControl.getETag();
		this.useCachedContent = cacheControl.useCachedContent();
		this.expirationTime = cacheControl.getExpirationTime();
		this.usePublicScope = cacheControl.isPublicScope();
	}
	public String getEtag() {
		return etag;
	}
	public void setEtag(String etag) {
		this.etag = etag;
	}
	public boolean isUseCachedContent() {
		return useCachedContent;
	}
	public void setUseCachedContent(boolean useCachedContent) {
		this.useCachedContent = useCachedContent;
	}
	public int getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(int expirationTime) {
		this.expirationTime = expirationTime;
	}
	public boolean isUsePublicScope() {
		return usePublicScope;
	}
	public void setUsePublicScope(boolean usePublicScope) {
		this.usePublicScope = usePublicScope;
	}
	@Override
	public String toString() {
		return "CacheControlFormBackingObject [etag=" + etag
				+ ", useCachedContent=" + useCachedContent
				+ ", expirationTime=" + expirationTime + ", usePublicScope="
				+ usePublicScope + "]";
	}
	
}
