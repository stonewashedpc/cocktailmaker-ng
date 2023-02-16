package com.stonewashedpc.cocktailmakerng.entities;

public class PumpInstruction implements Comparable<PumpInstruction> {
	
	private Pumpable pump;
	
	private Integer ml;

	public PumpInstruction(Pumpable pump, Integer ml) {
		super();
		this.pump = pump;
		this.ml = ml;
	}

	public Pumpable getPump() {
		return pump;
	}

	public Integer getMl() {
		return ml;
	}
	
	public Long getDuration() {
		return (long) Math.round(((double) this.ml / this.pump.getPumpSpeed()) * 60_000);
	}

	@Override
	public int compareTo(PumpInstruction o) {
		return this.getDuration().compareTo(o.getDuration());
	}
}
