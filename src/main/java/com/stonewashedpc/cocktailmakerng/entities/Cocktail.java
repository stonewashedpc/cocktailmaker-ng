package com.stonewashedpc.cocktailmakerng.entities;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.stonewashedpc.cocktailmakerng.entities.step.Serving;
import com.stonewashedpc.cocktailmakerng.entities.step.ServingCollectionStepVisitor;
import com.stonewashedpc.cocktailmakerng.entities.step.Step;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="class")
public class Cocktail {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Step> steps;
	
	public Cocktail() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonProperty("vol")
	public Double getVol() {
		List<Serving> servings = this.steps.stream().map(step -> {
			ServingCollectionStepVisitor visitor = new ServingCollectionStepVisitor();
			step.accept(visitor);
			return visitor.getResult();
		}).flatMap(List::stream).collect(Collectors.toList());
		
		Integer totalMl = 0;
		Double pureAlcoholMl = 0.0;
		for (Serving serving : servings) {
			totalMl += serving.getMilliliters();
			pureAlcoholMl += serving.getMilliliters() * serving.getIngredient().getVol();
		}
		return totalMl == 0 ? 0 : pureAlcoholMl / totalMl;
	}
}
