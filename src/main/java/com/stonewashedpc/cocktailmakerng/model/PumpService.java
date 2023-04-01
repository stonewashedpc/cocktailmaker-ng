package com.stonewashedpc.cocktailmakerng.model;

import java.util.Set;

import com.stonewashedpc.cocktailmakerng.entities.Ingredient;
import com.stonewashedpc.cocktailmakerng.entities.Pump;
import com.stonewashedpc.cocktailmakerng.exceptions.ElementNotFoundException;
import com.stonewashedpc.cocktailmakerng.exceptions.PumpControlException;

public interface PumpService extends Service<Pump, Integer> {
	
	public Set<Ingredient> findAvailableIngredients();
	public Set<Pump> findByIngredient(Ingredient ingredient) throws ElementNotFoundException;
	public long handleStepById(Long id) throws ElementNotFoundException, PumpControlException;
	public void tryAcquireMutex() throws PumpControlException;
	public void releaseMutex();
	public GpioService getGpioService();
}
