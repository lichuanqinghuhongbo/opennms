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

package org.opennms.web.filter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.criterion.Criterion;

/**
 * BaseFilter
 *
 * @author brozow
 */
public abstract class BaseFilter<T> implements Filter {
    
    protected String m_filterName;
    protected SQLType<T> m_sqlType;
    private String m_fieldName;
    private String m_propertyName;
    
    public BaseFilter(String filterType, SQLType<T> sqlType, String fieldName, String propertyName) {
        m_filterName = filterType;
        m_sqlType = sqlType;
        m_fieldName = fieldName;
        m_propertyName = propertyName;
    }


    public String getSQLFieldName() {
        return m_fieldName;
    }
    
    public String getPropertyName() {
        return m_propertyName;
    }

    public final String getDescription() {
        return m_filterName+"="+getValueString();
    }
    
    final public void bindValue(PreparedStatement ps, int parameterIndex, T value) throws SQLException {
        m_sqlType.bindParam(ps, parameterIndex, value);
    }
    
    public String formatValue(T value) {
        return m_sqlType.formatValue(value);
    }
    
    final public String getValueAsString(T value) {
        return m_sqlType.getValueAsString(value);
    }
    
    public abstract String getValueString();
    
    public abstract Criterion getCriterion();

    public abstract int bindParam(PreparedStatement ps, int parameterIndex) throws SQLException;

    public abstract String getParamSql();

    public abstract String getSql();

    public abstract String getTextDescription();

    public String toString() {
        return new ToStringBuilder(this)
            .append("description", getDescription())
            .append("text description", getTextDescription())
            .append("SQL field name", getSQLFieldName())
            .append("property name", getPropertyName())
            .toString();
    }
}
