/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * Copyright (C) 2002-2004, 2006-2009 The OpenNMS Group, Inc.  All rights reserved.
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


package org.opennms.web.notification;

import java.util.StringTokenizer;

import org.opennms.web.WebSecurityUtils;
import org.opennms.web.filter.Filter;
import org.opennms.web.notification.filter.AcknowledgedByFilter;
import org.opennms.web.notification.filter.InterfaceFilter;
import org.opennms.web.notification.filter.NodeFilter;
import org.opennms.web.notification.filter.NotificationIdFilter;
import org.opennms.web.notification.filter.ResponderFilter;
import org.opennms.web.notification.filter.ServiceFilter;
import org.opennms.web.notification.filter.UserFilter;

public abstract class NoticeUtil extends Object {

    public static org.opennms.web.filter.Filter getFilter(String filterString) {
        Filter filter = null;

        StringTokenizer tokens = new StringTokenizer(filterString, "=");
        String type = tokens.nextToken();
        String value = tokens.nextToken();

        if (type.equals(AcknowledgedByFilter.TYPE)) {
            filter = new AcknowledgedByFilter(value);
        } else if (type.equals(InterfaceFilter.TYPE)) {
            filter = new InterfaceFilter(value);
        } else if (type.equals(NodeFilter.TYPE)) {
            filter = new NodeFilter(WebSecurityUtils.safeParseInt(value));
        } else if (type.equals(NotificationIdFilter.TYPE)) {
            filter = new NotificationIdFilter(WebSecurityUtils.safeParseInt(value));
        } else if (type.equals(ResponderFilter.TYPE)) {
            filter = new ResponderFilter(value);
        } else if (type.equals(ServiceFilter.TYPE)) {
            filter = new ServiceFilter(WebSecurityUtils.safeParseInt(value));
        } else if (type.equals(UserFilter.TYPE)) {
            filter = new UserFilter(value);
        }

        return filter;
    }

}
