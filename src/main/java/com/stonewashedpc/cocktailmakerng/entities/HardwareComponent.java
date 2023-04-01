package com.stonewashedpc.cocktailmakerng.entities;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.stonewashedpc.cocktailmakerng.model.GpioService;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="class")
public abstract class HardwareComponent {
	
	@Id
	private Integer bcm;

	public HardwareComponent(Integer bcm) {
		super();
		this.setBcm(bcm);
	}
	
	public HardwareComponent() {
		super();
	}

	protected void setHigh(GpioService service) {
		service.getDigitalOutput(this.bcm).high();
		System.out.println("BCM-Pin " + bcm + " set to high.");
	}
	
	protected void setLow(GpioService service) {
		service.getDigitalOutput(this.bcm).low();
		System.out.println("BCM-Pin " + bcm + " set to low.");
	}

	public Integer getBcm() {
		return bcm;
	}

	public void setBcm(Integer bcm) {
		this.bcm = bcm;
	}
}
