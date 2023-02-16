package com.stonewashedpc.cocktailmakerng.entities.step;

import jakarta.persistence.Entity;

@Entity
public class ManualStep extends Step {
	
	private String description;
	
	public ManualStep(Long id) {
		super(id);
	}
	
	public ManualStep() {
		super();
	}

	public void handle() {
		// TODO
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void accept(StepVisitor<?, ?> visitor) {
		visitor.handle(this);
	}

}
