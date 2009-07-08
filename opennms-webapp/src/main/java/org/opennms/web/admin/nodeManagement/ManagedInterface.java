/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * Copyright (C) 2002-2004, 2006-2008 The OpenNMS Group, Inc.  All rights reserved.
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


package org.opennms.web.admin.nodeManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * A servlet that stores interface information
 * 
 * @author <A HREF="mailto:jason@opennms.org">Jason Johns </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 */
public class ManagedInterface {
    /**
     * 
     */
    protected String address;

    /**
     * 
     */
    protected List<ManagedService> services;

    /**
     * 
     */
    protected String status;

    /**
     * 
     */
    protected int nodeid;

    /**
     */
    public ManagedInterface() {
        services = new ArrayList<ManagedService>();
    }

    /**
     */
    public void addService(ManagedService newService) {
        services.add(newService);
    }

    /**
     * 
     */
    public void setAddress(String newAddress) {
        address = newAddress;
    }

    /**
     */
    public String getAddress() {
        return address;
    }

    /**
     */
    public List<ManagedService> getServices() {
        return services;
    }

    /**
     * 
     */
    public int getServiceCount() {
        return services.size();
    }

    /**
     * 
     */
    public void setNodeid(int id) {
        nodeid = id;
    }

    /**
     * 
     */
    public int getNodeid() {
        return nodeid;
    }

    /**
     * 
     */
    public void setStatus(String newStatus) {
        if (newStatus.equals("M")) {
            status = "managed";
        } else if (newStatus.equals("A")) {
            status = "managed";
        } else {
            status = "unmanaged";
        }
    }

    /**
     * 
     */
    public String getStatus() {
        return status;
    }
}
