package com.stonewashedpc.cocktailmakerng.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Pump extends HardwareComponent implements Pumpable {

	private Integer pumpSpeed; // Pump speed in ml/s
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Ingredient ingredient;
	
	public Pump(Integer bcm, Integer pumpSpeed, Ingredient ingredient) {
		super(bcm);
		this.pumpSpeed = pumpSpeed;
		this.ingredient = ingredient;
	}
	
	public Pump() {
		super();
	}
	
	@Override
	public Integer getPumpSpeed() {
		return pumpSpeed;
	}

	public void setPumpSpeed(Integer pumpSpeed) {
		this.pumpSpeed = pumpSpeed;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	@Override
	public void start() {
		this.setHigh();
	}

	@Override
	public void stop() {
		this.setLow();
	}
}
