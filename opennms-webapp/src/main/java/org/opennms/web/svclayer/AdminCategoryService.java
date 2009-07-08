/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * Copyright (C) 2006-2008 The OpenNMS Group, Inc.  All rights reserved.
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

import java.util.List;

import org.opennms.netmgt.model.OnmsCategory;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.web.svclayer.support.DefaultAdminCategoryService.CategoryAndMemberNodes;
import org.opennms.web.svclayer.support.DefaultAdminCategoryService.EditModel;
import org.opennms.web.svclayer.support.DefaultAdminCategoryService.NodeEditModel;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author <a href="mailto:dj@opennms.org">DJ Gregor</a>
 */
@Transactional(readOnly = true)
public interface AdminCategoryService {
    public CategoryAndMemberNodes getCategory(String categoryIdString);

    public List<OnmsNode> findAllNodes();
    
    public EditModel findCategoryAndAllNodes(String categoryIdString);

    @Transactional(readOnly = false)
    public void performEdit(String editAction, String editAction2, String[] toAdd, String[] toDelete);

    @Transactional(readOnly = false)
    public OnmsCategory addNewCategory(String name);

    public List<OnmsCategory> findAllCategories();

    @Transactional(readOnly = false)
    public void removeCategory(String categoryIdString);

    public List<OnmsCategory> findByNode(int nodeId);

    public NodeEditModel findNodeCategories(String nodeIdString);

    @Transactional(readOnly = false)
    public void performNodeEdit(String nodeIdString, String editAction, String[] toAdd, String[] toDelete);

}
