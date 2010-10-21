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

package org.jasig.portlet.test.event;

import java.util.Date;

import javax.portlet.ActionResponse;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.util.PortletUtils;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
@Controller
@RequestMapping("VIEW")
public class EventTestController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @RenderMapping("NORMAL")
    public String viewEventsTest(
            @RequestParam(value = "simpleEvent", required = false) String simpleEvent,
            PortletSession portletSession,
            ModelMap model) {

        logger.debug("Rendering Event Test");
        
        if (simpleEvent != null) {
            model.put("simpleEvent", simpleEvent);
        }
        
        final Long eventCount;
        synchronized (PortletUtils.getSessionMutex(portletSession)) {
            eventCount = (Long)portletSession.getAttribute("EndlessTestEvent");
        }
        if (eventCount != null) {
            model.put("endlessEvent", eventCount);
        }
        
        return "eventTest";
    }
    
    @ActionMapping("simpleEventTest")
    public void simpleEventTest(ActionResponse response) {
        final Date now = new Date();
        logger.debug("Sending SimpleTestEvent={} from action handler", now);
        
        response.setEvent("SimpleTestEvent", now);
    }
    
    @EventMapping("SimpleTestEvent")
    public void simpleEventHandler(EventRequest  eventRequest, EventResponse eventResponse) {
        final Event event = eventRequest.getEvent();
        logger.debug("Handling SimpleTestEvent: {}", event);
        
        final Date value = (Date)event.getValue();
        
        logger.debug("Setting render parameter simpleEvent={}", value);
        eventResponse.setRenderParameter("simpleEvent", value.toString());
    }
    
    
    @ActionMapping("endlessEventTest")
    public void exponentialEventTest(ActionResponse response) {
        logger.debug("Sending EndlessTestEvent={} from action handler", 0);
        response.setEvent("EndlessTestEvent", 0);
    }
    
    @EventMapping("EndlessTestEvent")
    public void exponentialEventTest(EventRequest  eventRequest, EventResponse eventResponse) {
        final Event event = eventRequest.getEvent();
        logger.debug("Handling EndlessTestEvent: " + event);
        
        final Long eventCount = (Long)event.getValue();
        
        final PortletSession portletSession = eventRequest.getPortletSession();
        synchronized (PortletUtils.getSessionMutex(portletSession)) {
            portletSession.setAttribute("EndlessTestEvent", eventCount);
        }
        
        final long newCount = eventCount + 1;
        logger.debug("Sending EndlessTestEvent=" + newCount + " from event handler");
        eventResponse.setEvent("EndlessTestEvent", newCount);
    }
}
