/* *********************************************************************** *
 * project: org.matsim.*
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2018 by the members listed in the COPYING,        *
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

package org.matsim.vsp.ev.dvrp;

import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.Predicate;

import org.matsim.api.core.v01.network.Network;
import org.matsim.contrib.dvrp.data.Vehicle;
import org.matsim.contrib.dvrp.router.DvrpRoutingNetworkProvider;
import org.matsim.core.controler.AbstractModule;
import org.matsim.vsp.ev.charging.ChargingLogic;
import org.matsim.vsp.ev.charging.ChargingStrategy;
import org.matsim.vsp.ev.charging.ChargingWithQueueingAndAssignmentLogic;
import org.matsim.vsp.ev.data.Charger;
import org.matsim.vsp.ev.data.ChargingInfrastructure;
import org.matsim.vsp.ev.discharging.AuxEnergyConsumption;

import com.google.inject.Key;
import com.google.inject.name.Names;

/**
 * @author michalm
 */
public class EvDvrpIntegrationModule extends AbstractModule {
	private final Function<Charger, ChargingStrategy> chargingStrategyFactory;
	private final DoubleSupplier temperatureProvider;
	private final Predicate<Vehicle> isTurnedOnPredicate;

	public EvDvrpIntegrationModule(Function<Charger, ChargingStrategy> chargingStrategyFactory,
			DoubleSupplier temperatureProvider, Predicate<Vehicle> isTurnedOnPredicate) {
		this.chargingStrategyFactory = chargingStrategyFactory;
		this.temperatureProvider = temperatureProvider;
		this.isTurnedOnPredicate = isTurnedOnPredicate;
	}

	@Override
	public void install() {
		bind(Network.class).annotatedWith(Names.named(ChargingInfrastructure.CHARGERS))//
				.to(Key.get(Network.class, Names.named(DvrpRoutingNetworkProvider.DVRP_ROUTING))).asEagerSingleton();
		bind(ChargingLogic.Factory.class).toInstance(
				charger -> new ChargingWithQueueingAndAssignmentLogic(charger, chargingStrategyFactory.apply(charger)));
		bind(AuxEnergyConsumption.Factory.class)
				.toInstance(new DvrpAuxConsumptionFactory(temperatureProvider, isTurnedOnPredicate));
	}
}