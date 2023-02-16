package com.stonewashedpc.cocktailmakerng.entities;

import java.util.ArrayList;
import java.util.List;

import com.stonewashedpc.cocktailmakerng.entities.step.ManualStep;
import com.stonewashedpc.cocktailmakerng.entities.step.ParallelMachineStep;
import com.stonewashedpc.cocktailmakerng.entities.step.SequentialMachineStep;
import com.stonewashedpc.cocktailmakerng.entities.step.Serving;
import com.stonewashedpc.cocktailmakerng.entities.step.Step;

public class CocktailBuilder {
	
	private String name;
	
	private List<Step> steps;
	
	public CocktailBuilder(String name) {
		this.name = name;
		this.steps = new ArrayList<>();
	}
	
	public CocktailBuilder addManualStep(String description) {
		ManualStep step = new ManualStep();
		step.setDescription(description);
		this.steps.add(step);
		return this;
	}
	
	public CocktailBuilder addSequentialStep(List<Serving> servings) {
		SequentialMachineStep step = new SequentialMachineStep();
		step.setServings(servings);
		this.steps.add(step);
		return this;
	}
	
	public CocktailBuilder addParallelStep(List<Serving> servings) {
		ParallelMachineStep step = new ParallelMachineStep();
		step.setServings(servings);
		this.steps.add(step);
		return this;
	}
	
	public Cocktail build() throws Exception {
		if(this.name != null && !this.steps.isEmpty()) {
			Cocktail cocktail = new Cocktail();
			cocktail.setName(name);
			cocktail.setSteps(steps);
			return cocktail;
		} else throw new Exception("Cannot build cocktail.");
	}
}
