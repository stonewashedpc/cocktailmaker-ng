package com.stonewashedpc.cocktailmakerng.entities.step;

public abstract class StepVisitor<T, E extends Throwable> {
	
	private T result;
	
	private E exception;

	public abstract void handle(SequentialMachineStep sequentialMachineStep);

	public abstract void handle(ParallelMachineStep parallelMachineStep);

	public abstract void handle(ManualStep manualStep);

	public T getResult() {
		return result;
	}
	
	public T tryGetResult() throws E {
		if (exception != null) throw exception;
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public void setException(E exception) {
		this.exception = exception;
	}

}
