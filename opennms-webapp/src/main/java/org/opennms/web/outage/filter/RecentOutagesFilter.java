/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * Copyright (C) 2009 The OpenNMS Group, Inc.  All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc.:
 *
 *      51 Franklin Street
 *      5th Floor
 *      Boston, MA 02110-1301
 *      USA
 *
 * For more information contact:
 *
 *      OpenNMS Licensing <license@opennms.org>
 *      http://www.opennms.org/
 *      http://www.opennms.com/
 *
 *******************************************************************************/


package org.opennms.web.outage.filter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.opennms.web.filter.OneArgFilter;
import org.opennms.web.filter.SQLType;

/**
 * @author brozow
 *
 */
public class RecentOutagesFilter extends OneArgFilter<Date> {
    public static final String TYPE = "recent";
    
    public RecentOutagesFilter() {
        this(yesterday());
    }
    
    public RecentOutagesFilter(Date since) {
        super(TYPE, SQLType.DATE, "OUTAGES.IFREGAINEDSERVICE", "ifRegainedService", since);
    }
    
    @Override
    public String getSQLTemplate() {
        return " (" + getSQLFieldName() + " > %s OR " + getSQLFieldName() + " IS NULL) ";
    }

    @Override
    public Criterion getCriterion() {
        return Restrictions.or(Restrictions.gt(getPropertyName(), getValue()), Restrictions.isNull(getPropertyName()));
    }

    @Override
    public String getTextDescription() {
        return "outage since " + getValueAsString(getValue());
    }
    
    public static Date yesterday() {
        Calendar cal = new GregorianCalendar();
        cal.add( Calendar.DATE, -1 );
        return cal.getTime();
    }

}
