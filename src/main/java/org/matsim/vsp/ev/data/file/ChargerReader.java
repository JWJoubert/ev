/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2013 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.vsp.ev.data.file;

import java.util.Map;
import java.util.Stack;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.contrib.dvrp.data.file.ReaderUtils;
import org.matsim.core.utils.io.MatsimXmlParser;
import org.matsim.vsp.ev.EvUnitConversions;
import org.matsim.vsp.ev.data.Charger;
import org.matsim.vsp.ev.data.ChargerImpl;
import org.matsim.vsp.ev.data.ChargingInfrastructureImpl;
import org.xml.sax.Attributes;

public class ChargerReader extends MatsimXmlParser {
	private final static String CHARGER = "charger";

	private final static int DEFAULT_CHARGER_CAPACITY = 1;
	private final static int DEFAULT_CHARGER_POWER_kW = 50;// [kW]

	private final ChargingInfrastructureImpl chargingInfrastructure;
	private Map<Id<Link>, ? extends Link> links;

	public ChargerReader(Network network, ChargingInfrastructureImpl chargingInfrastructure) {
		this.chargingInfrastructure = chargingInfrastructure;
		links = network.getLinks();
	}

	@Override
	public void startTag(String name, Attributes atts, Stack<String> context) {
		if (CHARGER.equals(name)) {
			chargingInfrastructure.addCharger(createCharger(atts));
		}
	}

	@Override
	public void endTag(String name, String content, Stack<String> context) {
	}

	private Charger createCharger(Attributes atts) {
		Id<Charger> id = Id.create(atts.getValue("id"), Charger.class);
		Link link = links.get(Id.createLinkId(atts.getValue("link")));
		double power_kW = ReaderUtils.getDouble(atts, "power", DEFAULT_CHARGER_POWER_kW);
		int plugs = ReaderUtils.getInt(atts, "capacity", DEFAULT_CHARGER_CAPACITY);
		return new ChargerImpl(id, power_kW * EvUnitConversions.W_PER_kW, plugs, link);
	}
}
