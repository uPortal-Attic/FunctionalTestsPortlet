/**
 * Copyright 2007 The JA-SIG Collaborative.  All rights reserved.
 * See license distributed with this file and
 * available online at http://www.uportal.org/license.html
 */
package org.jasig.portlet.test.om.prefs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class Preferences implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Preference> preferences = new ArrayList<Preference>(0);

    /**
     * @return the preferences
     */
    public List<Preference> getPreferences() {
        return preferences;
    }
    /**
     * @param preferences the preferences to set
     */
    public void setPreferences(List<Preference> preferences) {
        this.preferences = preferences;
    }
    
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Preferences)) {
            return false;
        }
        Preferences rhs = (Preferences) object;
        return new EqualsBuilder()
            .append(this.preferences, rhs.preferences)
            .isEquals();
    }
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(1984848547, 815848441)
            .append(this.preferences)
            .toHashCode();
    }
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("preferences", this.preferences)
                .toString();
    }
}
