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

import java.util.Comparator;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class PortletTestComparator implements Comparator<PortletTest> {
    public static final PortletTestComparator INSTANCE = new PortletTestComparator();

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(PortletTest o1, PortletTest o2) {
        if (o1 == o2) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        
        final String tn1 = o1.getTestKey();
        final String tn2 = o2.getTestKey();
        
        if (tn1 == tn2) {
            return 0;
        }
        if (tn1 == null) {
            return -1;
        }
        return tn1.compareTo(tn2);
    }
}
