package com.stonewashedpc.cocktailmakerng.entities.step;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="class")
@JsonSubTypes({ 
	@Type(value = ManualStep.class, name = "ManualStep"), 
	@Type(value = SequentialMachineStep.class, name = "SequentialMachineStep"), 
	@Type(value = ParallelMachineStep.class, name = "ParallelMachineStep")
})
public abstract class Step {
	
	@Id
	@GeneratedValue
	protected Long id;

	public abstract void handle();
	
	public Step() {
		super();
	}

	public Step(Long id) {
		super();
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public abstract void accept(StepVisitor<?, ?> visitor); // Beide Visitoren muessen den gleichen Supertyp haben
}
