package com.stonewashedpc.cocktailmakerng.entities;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.stonewashedpc.cocktailmakerng.exceptions.HardwareException;

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
	
	// private Context context; // Pi4J Context

	public HardwareComponent(Integer bcm) {
		super();
		this.bcm = bcm;
	}
	
	public HardwareComponent() {
		super();
	}

	protected void setHigh() {
		System.out.println("BCM-Pin " + bcm + " set to high.");
	}
	
	protected void setLow() {
		System.out.println("BCM-Pin " + bcm + " set to low.");
	}
	
	public void pulse(long millis) throws HardwareException {
		try {
			this.setHigh();
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new HardwareException("HardwareComponent interrupted before finishing pulse for " + millis + " ms.");
		} finally {
			this.setLow();
		}
	}

	public Integer getBcm() {
		return bcm;
	}

	public void setBcm(Integer bcm) {
		this.bcm = bcm;
	}
}
