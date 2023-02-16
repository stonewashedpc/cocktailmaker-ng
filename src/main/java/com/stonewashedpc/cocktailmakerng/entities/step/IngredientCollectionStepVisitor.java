package com.stonewashedpc.cocktailmakerng.entities.step;

import java.util.Set;
import java.util.stream.Collectors;

import com.stonewashedpc.cocktailmakerng.entities.Ingredient;

public class IngredientCollectionStepVisitor extends StepVisitor<Set<Ingredient>, Exception> {

	public void handle(SequentialMachineStep sequentialMachineStep) {
		this.setResult(sequentialMachineStep.getServings().stream().map(s -> s.getIngredient()).collect(Collectors.toSet()));
	}

	public void handle(ParallelMachineStep parallelMachineStep) {
		this.setResult(parallelMachineStep.getServings().stream().map(s -> s.getIngredient()).collect(Collectors.toSet()));
	}

	public void handle(ManualStep manualStep) {
		this.setResult(Set.of());
	}

}
