package com.stonewashedpc.cocktailmakerng.entities.step;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.stonewashedpc.cocktailmakerng.entities.Pump;
import com.stonewashedpc.cocktailmakerng.entities.PumpCluster;
import com.stonewashedpc.cocktailmakerng.entities.PumpInstruction;
import com.stonewashedpc.cocktailmakerng.exceptions.ElementNotFoundException;
import com.stonewashedpc.cocktailmakerng.exceptions.PumpControlException;
import com.stonewashedpc.cocktailmakerng.model.PumpService;

public class PumpControlStepVisitor extends StepVisitor<Long, PumpControlException> {

	private PumpService pumpService;

	public PumpControlStepVisitor(PumpService pumpService) {
		super();
		this.pumpService = pumpService;
	}

	@Override
	public void handle(SequentialMachineStep sequentialMachineStep) {

		try {
			this.pumpService.tryAcquireMutex();
			
			List<PumpInstruction> pumpInstructions = toPumpInstructions(sequentialMachineStep.getServings());
			
			new Thread(() -> {
				for (PumpInstruction pumpInstruction : pumpInstructions) {
					pumpInstruction.getPump().start();
					try {
						Thread.sleep(pumpInstruction.getDuration());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						// Make sure to turn off the pumps, even
						// if the thread was interrupted
						pumpInstruction.getPump().stop();
					}
				}
				// Release mutex
				this.pumpService.releaseMutex();
			}).start();

			this.setResult(pumpInstructions.stream().map(pumpInstruction -> pumpInstruction.getDuration())
					.reduce((long) 0, Long::sum));

		} catch (ElementNotFoundException e) {
			this.setException((PumpControlException) new PumpControlException("Step impossible with current pump configuration").initCause(e));
			// Also release mutex if step is impossible
			this.pumpService.releaseMutex();
		} catch (PumpControlException e) {
			this.setException(e);
		}
	}

	@Override
	public void handle(ParallelMachineStep parallelMachineStep) {

		try {
			this.pumpService.tryAcquireMutex();
			
			List<PumpInstruction> pumpInstructions = toPumpInstructions(parallelMachineStep.getServings());

			Collections.sort(pumpInstructions);

			new Thread(() -> {
				for (PumpInstruction pumpInstruction : pumpInstructions) {
					pumpInstruction.getPump().start();
				}
				
				try {
					long millisSinceStart = 0;
					for (PumpInstruction pumpInstruction : pumpInstructions) {
						long instructionDuration = pumpInstruction.getDuration();
						long sleepDuration = instructionDuration - millisSinceStart;
						if (sleepDuration > 0)
							Thread.sleep(instructionDuration - millisSinceStart);
						pumpInstruction.getPump().stop();
						millisSinceStart = instructionDuration;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					// Make sure to turn off the pumps,
					// if the thread was interrupted
					for (PumpInstruction pumpInstruction : pumpInstructions) {
						pumpInstruction.getPump().stop();
					}
				} finally {
					// Always release mutex in the end
					this.pumpService.releaseMutex();
				}
			}).start();

			this.setResult(pumpInstructions.stream().map(pumpInstruction -> pumpInstruction.getDuration())
					.reduce((long) 0, Long::max));

		} catch (ElementNotFoundException e) {
			this.setException((PumpControlException) new PumpControlException("Step impossible with current pump configuration").initCause(e));
			// Also release mutex if step is impossible
			this.pumpService.releaseMutex();
		} catch (PumpControlException e) {
			this.setException(e);
		}
	}

	@Override
	public void handle(ManualStep manualStep) {
		this.setResult((long) 0); // Return 0 as there is nothing to do
	}

	private boolean isPossible(List<Serving> servings) {
		return servings.stream().map(serving -> serving.getIngredient())
				.allMatch(this.pumpService.findAvailableIngredients()::contains);
	}

	private List<PumpInstruction> toPumpInstructions(List<Serving> servings) throws ElementNotFoundException {
		if (!isPossible(servings))
			throw new ElementNotFoundException();

		return servings.stream().map(serving -> {

			// The following should never fail due to the
			// previous isPossible-check
			Set<Pump> availPumps = null;
			try {
				availPumps = this.pumpService.findByIngredient(serving.getIngredient());
			} catch (ElementNotFoundException e) {
				e.printStackTrace();
			}

			if (availPumps.size() == 1) {
				return new PumpInstruction(availPumps.iterator().next(), serving.getMilliliters());
			} else {
				PumpCluster cluster = new PumpCluster(availPumps);
				return new PumpInstruction(cluster, serving.getMilliliters());
			}

		}).collect(Collectors.toList());
	}
}
