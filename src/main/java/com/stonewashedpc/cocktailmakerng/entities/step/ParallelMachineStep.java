package com.stonewashedpc.cocktailmakerng.entities.step;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class ParallelMachineStep extends Step {
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Serving> servings;

	public ParallelMachineStep(Long id, List<Serving> servings) {
		super(id);
		this.servings = servings;
	}

	public ParallelMachineStep() {
		super();
	}
	
	public List<Serving> getServings() {
		return servings;
	}

	public void setServings(List<Serving> servings) {
		this.servings = servings;
	}

	@Override
	public void accept(StepVisitor<?, ?> visitor) {
		visitor.handle(this);
	}

}
