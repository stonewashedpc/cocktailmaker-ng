package com.stonewashedpc.cocktailmakerng.entities;

import com.stonewashedpc.cocktailmakerng.model.GpioService;

public interface Pumpable {
	
	public void start(GpioService service);
	public void stop(GpioService service);
	public Integer getPumpSpeed();
}
