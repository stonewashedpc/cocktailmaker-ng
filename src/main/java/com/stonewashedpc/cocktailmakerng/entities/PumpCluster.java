package com.stonewashedpc.cocktailmakerng.entities;

import java.util.Set;

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
	public void start() {
		for (Pump pump : this.pumps) pump.setHigh();
	}

	@Override
	public void stop() {
		for (Pump pump : this.pumps) pump.setLow();
	}
}
