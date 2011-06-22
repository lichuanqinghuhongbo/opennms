/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2006-2011 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2011 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.linkd.snmp;

import java.net.InetAddress;

import org.opennms.netmgt.capsd.snmp.NamedSnmpVar;
import org.opennms.netmgt.capsd.snmp.SnmpTableEntry;

/**
 *<P>The IpNetToMediaTableEntry class is designed to hold all the MIB-II
 * information for one entry in the ipNetToMediaTable. The table effectively
 * contains a list of these entries, each entry having information
 * about one physical address. The entry contains the ifindex binding, the mac address,
 * ip address and entry type.</P>
 *
 * <P>This object is used by the IpNetToMediaTable to hold infomation
 * single entries in the table. See the IpNetToMediaTable documentation
 * form more information.</P>
 *
 * @author <A HREF="mailto:rssntn67@yahoo.it">Antonio</A>
 * @see IpNetToMediaTable
 * @see <A HREF="http://www.ietf.org/rfc/rfc1213.txt">RFC1213</A>
 * @version $Id: $
 */
public final class IpNetToMediaTableEntry extends SnmpTableEntry
{
	// Lookup strings for specific table entries
	//
	/** Constant <code>INTM_INDEX="ipNetToMediaIfIndex"</code> */
	public final static	String	INTM_INDEX		= "ipNetToMediaIfIndex";
	/** Constant <code>INTM_PHYSADDR="ipNetToMediaPhysAddress"</code> */
	public final static	String	INTM_PHYSADDR	= "ipNetToMediaPhysAddress";
	/** Constant <code>INTM_NETADDR="ipNetToMediaNetAddress"</code> */
	public final static	String	INTM_NETADDR	= "ipNetToMediaNetAddress";
	/** Constant <code>INTM_TYPE="ipNetToMediatype"</code> */
	public final static	String	INTM_TYPE		= "ipNetToMediatype";

	/**
	 * <P>The keys that will be supported by default from the 
	 * TreeMap base class. Each of the elements in the list
	 * are an instance of the IpNetToMediatable. Objects
	 * in this list should be used by multiple instances of
	 * this class.</P>
	 */
	public static NamedSnmpVar[]	ms_elemList = null;
	
	/**
	 * <P>Initialize the element list for the class. This
	 * is class wide data, but will be used by each instance.</P>
	 */
	static
	{
		ms_elemList = new NamedSnmpVar[4];
		int ndx = 0;
		
		/**
		 * The interface on which this entry's equivalence
 		 * is effective. The interface identified by a
 		 * particular value of this index is the same
 		 * interface as identified by the same value of
 		 * ifIndex.
		 */
		ms_elemList[ndx++] = new NamedSnmpVar(NamedSnmpVar.SNMPINT32,			INTM_INDEX,		".1.3.6.1.2.1.4.22.1.1",  1);

		/**
		 * The media-dependent `physical' address. 
		 */
		ms_elemList[ndx++] = new NamedSnmpVar(NamedSnmpVar.SNMPOCTETSTRING,	INTM_PHYSADDR,	".1.3.6.1.2.1.4.22.1.2",  2);
		
		/**
		 * The IpAddress corresponding to the media-
		 * dependent `physical' address.
		 */
		ms_elemList[ndx++] = new NamedSnmpVar(NamedSnmpVar.SNMPIPADDRESS,		INTM_NETADDR,	".1.3.6.1.2.1.4.22.1.3",  3);
		
		/**
		 * The type of mapping.
 		 * Setting this object to the value invalid(2) has
 		 * the effect of invalidating the corresponding entry
 		 * in the ipNetToMediaTable. That is, it effectively
 		 * disassociates the interface identified with said
 		 * entry from the mapping identified with said entry.
 		 * It is an implementation-specific matter as to
 		 * whether the agent removes an invalidated entry
 		 * from the table. Accordingly, management stations
 		 * must be prepared to receive tabular information
 		 * from agents that corresponds to entries not
 		 * currently in use. Proper interpretation of such
 		 * entries requires examination of the relevant
 		 * ipNetToMediaType object.
		 */
		ms_elemList[ndx++] = new NamedSnmpVar(NamedSnmpVar.SNMPINT32,			INTM_TYPE,		".1.3.6.1.2.1.4.22.1.4",  4);
	}
	

	/**
	 * <P>The TABLE_OID is the object identifier that represents
	 * the root of the IP Address table in the MIB forest.</P>
	 */
	public static final String	TABLE_OID	= ".1.3.6.1.2.1.4.22.1";	// start of table (GETNEXT)

	/**
	 * <P>Creates a default instance of the ipNetToMedia
	 * table entry map. The map represents a singular
	 * instance of the mac address table. Each column in
	 * the table for the loaded instance may be retreived
	 * either through its name or object identifier.</P>
	 *
	 * <P>The initial table is constructied with zero
	 * elements in the map.</P>
	 */
	public IpNetToMediaTableEntry( )
	{
		super(ms_elemList);
	}

	/**
	 * <p>getIpNetToMediaIfIndex</p>
	 *
	 * @return a int.
	 */
	public int getIpNetToMediaIfIndex(){
		Integer val = getInt32(IpNetToMediaTableEntry.INTM_INDEX);
		if (val == null) return -1;
		return val;
	}
	
	/**
	 * <p>getIpNetToMediaPhysAddress</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getIpNetToMediaPhysAddress(){
		return getHexString(IpNetToMediaTableEntry.INTM_PHYSADDR);
	}
	
	/**
	 * <p>getIpNetToMediaNetAddress</p>
	 *
	 * @return a {@link java.net.InetAddress} object.
	 */
	public InetAddress getIpNetToMediaNetAddress(){
		return getIPAddress(IpNetToMediaTableEntry.INTM_NETADDR);
	}
	
	/**
	 * <p>getIpNetToMediatype</p>
	 *
	 * @return a int.
	 */
	public int getIpNetToMediatype(){
		Integer val = getInt32(IpNetToMediaTableEntry.INTM_TYPE);
		if (val == null) return -1;
		return val;
	}

}   
