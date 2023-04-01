package com.stonewashedpc.cocktailmakerng.entities;

import java.util.Set;

import com.stonewashedpc.cocktailmakerng.model.GpioService;

public class PumpCluster implements Pumpable {

	private Set<Pump> pumps;

	public PumpCluster(Set<Pump> pumps) {
		super();
		this.pumps = pumps;
	}
	
	@Override
	public Integer getPumpSpeed() {
		return this.pumps.stream().map(p -> p.getPumpSpeed()).reduce(0, Integer::sum);
	}

	@Override
	public void start(GpioService service) {
		for (Pump pump : this.pumps) pump.setLow(service);
	}

	@Override
	public void stop(GpioService service) {
		for (Pump pump : this.pumps) pump.setHigh(service);
	}
}
