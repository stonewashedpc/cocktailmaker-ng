package com.stonewashedpc.cocktailmakerng.model;

import com.pi4j.io.gpio.digital.DigitalOutput;

public interface GpioService {
	public DigitalOutput getDigitalOutput(Integer bcmPin);
}
