/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2016 by the members listed in the COPYING,        *
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

package org.matsim.vsp.ev.data;

import org.matsim.api.core.v01.Id;
import org.matsim.vsp.ev.discharging.AuxEnergyConsumption;
import org.matsim.vsp.ev.discharging.DriveEnergyConsumption;

public class ElectricVehicleImpl implements ElectricVehicle {
	private final Id<ElectricVehicle> vehicleId;
	private Battery battery;// not final -- can be swapped

	private DriveEnergyConsumption driveEnergyConsumption;
	private AuxEnergyConsumption auxEnergyConsumption;

	public ElectricVehicleImpl(Id<ElectricVehicle> vehicleId, Battery battery) {
		this.vehicleId = vehicleId;
		this.battery = battery;
	}

	@Override
	public Id<ElectricVehicle> getId() {
		return vehicleId;
	}

	@Override
	public Battery getBattery() {
		return battery;
	}

	@Override
	public DriveEnergyConsumption getDriveEnergyConsumption() {
		return driveEnergyConsumption;
	}

	@Override
	public AuxEnergyConsumption getAuxEnergyConsumption() {
		return auxEnergyConsumption;
	}

	@Override
	public Battery swapBattery(Battery battery) {
		Battery old = this.battery;
		this.battery = battery;
		return old;
	}

	@Override
	public void setDriveEnergyConsumption(DriveEnergyConsumption driveEnergyConsumption) {
		this.driveEnergyConsumption = driveEnergyConsumption;
	}

	@Override
	public void setAuxEnergyConsumption(AuxEnergyConsumption auxEnergyConsumption) {
		this.auxEnergyConsumption = auxEnergyConsumption;
	}
}
