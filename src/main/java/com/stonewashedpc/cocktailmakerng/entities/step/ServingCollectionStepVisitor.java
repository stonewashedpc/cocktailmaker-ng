package com.stonewashedpc.cocktailmakerng.entities.step;

import java.util.List;

public class ServingCollectionStepVisitor extends StepVisitor<List<Serving>, Exception> {

	@Override
	public void handle(SequentialMachineStep sequentialMachineStep) {
		this.setResult(sequentialMachineStep.getServings());
	}

	@Override
	public void handle(ParallelMachineStep parallelMachineStep) {
		this.setResult(parallelMachineStep.getServings());
		
	}

	@Override
	public void handle(ManualStep manualStep) {
		this.setResult(List.of());
	}

}
