package com.stonewashedpc.cocktailmakerng.entities;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="class")
public class Ingredient {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private Double vol = 0.0;

	public Ingredient(String name) {
		super();
		this.name = name;
	}
	
	public Ingredient(String name, Double vol) {
		super();
		this.name = name;
		this.vol = vol;
	}

	public Ingredient() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getVol() {
		return vol;
	}

	public void setVol(Double vol) {
		this.vol = vol;
	}
	
}
