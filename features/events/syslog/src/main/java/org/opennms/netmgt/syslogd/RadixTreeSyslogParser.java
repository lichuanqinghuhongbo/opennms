/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2017-2017 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2017 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.syslogd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import org.opennms.netmgt.config.SyslogdConfig;
import org.opennms.netmgt.model.events.EventBuilder;

/**
 * This parser reads a set of grok patterns that are stored in the 
 * <i>grok-patterns.txt</i> classpath resource and uses the patterns to
 * construct a syslog message parser.
 * 
 * @author Seth
 */
public class RadixTreeSyslogParser extends SyslogParser {

	private static RadixTreeParser radixParser = new RadixTreeParser();

	static {
		new BufferedReader(new InputStreamReader(RadixTreeSyslogParser.class.getClassLoader().getResourceAsStream("org/opennms/netmgt/syslogd/grok-patterns.txt"))).lines().forEach(pattern -> {
			// Ignore comments and blank lines
			if (pattern == null || pattern.trim().length() == 0 || pattern.trim().startsWith("#")) {
				return;
			}
			radixParser.teach(GrokParserStageSequenceBuilder.parseGrok(pattern).toArray(new ParserStage[0]));
		});
		System.out.println("Parser tree: " + radixParser.tree.toString());
	}

	public RadixTreeSyslogParser(SyslogdConfig config, ByteBuffer syslogString) {
		super(config, syslogString);
	}

	/**
	 * Since this parser does not rely on a regex expression match for its initial
	 * parsing, always return true.
	 */
	@Override
	public boolean find() {
		return true;
	}

	@Override
	public EventBuilder parseToEventBuilder(String systemId, String location) {
		EventBuilder bldr = radixParser.parse(getText()).join();
		if (bldr != null) {
			bldr.setDistPoller(systemId);
			// TODO: Set nodeid based on location
		}
		return bldr;
	}
}
