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
package org.jasig.portlet.test.cachecontrol.publicscope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Subclass of {@link GenericPortlet} intended to mimic the validation
 * {@link CacheControl} example in the Portlet 2.0 specification (section PLT.22.2).
 * 
 * This portlet demonstrates the cache control behavior by exposing a simple message service.
 * The view displays a form where a visitor can send a message. If there are no new messages,
 * the portlet tells the portal to use the cached content (see {@link javax.portlet.CacheControl#setUseCachedContent(boolean)}.
 * 
 * This portlet is separate from the {@link CacheControlTestController} for 2 purposes:
 * <ol>
 * <li>To demonstrate that the unmodified example in the portlet spec functions as expected</li>
 * <li>This portlet has it's own portlet definition in which PUBLIC scope is specified</li>
 * </ol>
 * 
 * @author Nicholas Blair
 * @version $Id$
 */
public class CacheControlTestPortlet extends GenericPortlet {

	private MessageService service = new MessageServiceImpl();
	/**
	 * Example copied from Portlet version 2.0 Specification, section PLT.22.2, with
	 * minor modifications.
	 * 
	 * (non-Javadoc)
	 * @see javax.portlet.GenericPortlet#doView(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		if ( request.getETag() != null ) {
			// validation request
			if ( markupIsStillValid() ) {
			
				// markup is still valid
				response.getCacheControl().setExpirationTime(30);
				response.getCacheControl().setUseCachedContent(true);
				return;
			}
			
		}
		// create new content with new validation tag
		final String newEtag = RandomStringUtils.randomAlphabetic(12);
		response.getCacheControl().setETag(newEtag);
		response.getCacheControl().setExpirationTime(60);

		request.setAttribute("lastRenderUser", request.getRemoteUser());
		request.setAttribute("lastRenderTime", new Date());
		request.setAttribute("messages", this.service.getMessages());
		PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher("/WEB-INF/jsp/cacheControlGenericPortletView.jsp");

		rd.include(request, response);
	}
	
	/**
	 * Add a new message, if the "message" request parameter is not empty.
	 * 
	 * (non-Javadoc)
	 * @see javax.portlet.GenericPortlet#processAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
	 */
	@Override
	@ProcessAction(name="addMessage")
	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException {
		final String what = request.getParameter("message");
		if(StringUtils.isNotBlank(what)) {
			final String who = request.getRemoteUser();
			Message newMessage = new Message(who, what);
			this.service.storeMessage(newMessage);
		}
	}

	/**
	 * Implemented by inverse of {@link MessageService#hasNewMessages()}.
	 * 
	 * @return
	 */
	protected boolean markupIsStillValid() {
		return !service.hasNewMessages();
	}
	
	
	/**
	 * Bean to represent a message.
	 * 
	 * @author Nicholas Blair
	 * @version $Id$
	 */
	private static class Message {
		private final String who;
		private final String what;
		private final Date when;
		/**
		 * @param who
		 * @param what
		 */
		private Message(String who, String what) {
			this.who = who;
			this.what = what;
			this.when = new Date();
		}
		@Override
		public String toString() {
			return who + " said '" + what + "' at " + when;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((what == null) ? 0 : what.hashCode());
			result = prime * result + ((when == null) ? 0 : when.hashCode());
			result = prime * result + ((who == null) ? 0 : who.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Message other = (Message) obj;
			if (what == null) {
				if (other.what != null)
					return false;
			} else if (!what.equals(other.what))
				return false;
			if (when == null) {
				if (other.when != null)
					return false;
			} else if (!when.equals(other.when))
				return false;
			if (who == null) {
				if (other.who != null)
					return false;
			} else if (!who.equals(other.who))
				return false;
			return true;
		}
		
	}
	/**
	 * 
	 * @author Nicholas Blair
	 * @version $Id$
	 */
	private static interface MessageService {
		void storeMessage(Message message);
		List<Message> getMessages();
		boolean hasNewMessages();
	}
	/**
	 * Simple {@link MessageService}.
	 * 
	 * @author Nicholas Blair
	 * @version $Id$
	 */
	private static class MessageServiceImpl implements MessageService {

		private LinkedList<Message> messages = new LinkedList<Message>();
		private Message lastReadMessage = null;
		
		@Override
		public synchronized void storeMessage(Message message) {
			messages.add(message);
		}
		@Override
		public synchronized List<Message> getMessages() {
			lastReadMessage = messages.peekLast();
			return new ArrayList<Message>(messages);
		}
		@Override
		public synchronized boolean hasNewMessages() {
			if(lastReadMessage == null) {
				return false;
			} else {
				Message currentLast = this.messages.peekLast();
				return !lastReadMessage.equals(currentLast);
			}
		}
	}
}
