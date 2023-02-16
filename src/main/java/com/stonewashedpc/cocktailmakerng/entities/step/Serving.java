package com.stonewashedpc.cocktailmakerng.entities.step;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.stonewashedpc.cocktailmakerng.entities.Ingredient;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="class")
public class Serving {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Ingredient ingredient;
	
	private Integer milliliters;
	
	public Serving(Ingredient ingredient, Integer milliliters) {
		super();
		this.ingredient = ingredient;
		this.milliliters = milliliters;
	}

	public Serving() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Integer getMilliliters() {
		return milliliters;
	}

	public void setMilliliters(Integer milliliters) {
		this.milliliters = milliliters;
	}
}
