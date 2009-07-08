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

package org.opennms.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class StoreRequestPropertiesFilter implements Filter {

    private String m_servletPathAttribute;
    private String m_relativeServletPathAttribute;

    public void init(FilterConfig config) throws ServletException {
        m_servletPathAttribute = config.getInitParameter("servletPathAttribute");
        m_relativeServletPathAttribute = config.getInitParameter("relativeServletPathAttribute");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (m_servletPathAttribute != null) {
            request.setAttribute(m_servletPathAttribute,
                                 httpRequest.getServletPath());
        }
        if (m_relativeServletPathAttribute != null) {
            String servletPath = httpRequest.getServletPath();
            if (servletPath != null && servletPath.length() > 0 && servletPath.charAt(0) == '/') {
                servletPath = servletPath.substring(1);
            }
            request.setAttribute(m_relativeServletPathAttribute,
                                 servletPath);
        }
        
        chain.doFilter(request, response);
    }

    public void destroy() {
        // Nothing to destroy that a GC won't take care of. :-)
    }

}
