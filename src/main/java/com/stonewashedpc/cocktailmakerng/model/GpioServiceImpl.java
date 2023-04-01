package com.stonewashedpc.cocktailmakerng.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;

@Service
public class GpioServiceImpl implements GpioService {
	
	private Context pi4j;
	
	private Map<Integer, DigitalOutput> outputs;
	
	public GpioServiceImpl() {
		super();
		this.pi4j = Pi4J.newAutoContext();
		this.outputs = new HashMap<>();
	}

	@Override
	public DigitalOutput getDigitalOutput(Integer bcmPin) {
		return outputs.computeIfAbsent(bcmPin, b -> {
			DigitalOutputConfigBuilder config = DigitalOutput.newConfigBuilder(this.pi4j)
					.id("BCM" + b)
					.name("BCM Pin " + b)
					.address(b)
					.shutdown(DigitalState.HIGH)
					.initial(DigitalState.HIGH)
					.provider("pigpio-digital-output");
			return this.pi4j.create(config);
		});
	}

}
