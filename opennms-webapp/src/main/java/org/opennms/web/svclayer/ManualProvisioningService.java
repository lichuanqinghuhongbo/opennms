/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * Copyright (C) 2006-2009 The OpenNMS Group, Inc.  All rights reserved.
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

package org.opennms.web.svclayer;

import java.util.Collection;
import java.util.Map;

import org.opennms.netmgt.provision.persist.requisition.Requisition;

/**
 * 
 * @author <a href="mailto:brozow@opennms.org">Mathew Brozowski</a>
 */
public interface ManualProvisioningService {
    
    Collection<String> getProvisioningGroupNames();
    
    Requisition getProvisioningGroup(String name);
    
    Requisition createProvisioningGroup(String name);

    Requisition saveProvisioningGroup(String groupName, Requisition groupData);
    
    Requisition addNewNodeToGroup(String groupName, String nodeLabel);
    
    Requisition addCategoryToNode(String groupName, String pathToNode, String categoryName);
    
    Requisition addAssetFieldToNode(String groupName, String pathToNode, String fieldName, String fieldValue);
    
    Requisition addInterfaceToNode(String groupName, String pathToNode, String ipAddr);
    
    Requisition addServiceToInterface(String groupName, String pathToInterface, String serviceName);
    
    Requisition deletePath(String groupName, String pathToDelete);
    
    void importProvisioningGroup(String groupName);

    Collection<Requisition> getAllGroups();

    void deleteProvisioningGroup(String groupName);

    void deleteAllNodes(String groupName);

    Map<String, Integer> getGroupDbNodeCounts();

    Collection<String> getNodeCategoryNames();

    Collection<String> getAssetFieldNames();

    Collection<String> getServiceTypeNames();

}
