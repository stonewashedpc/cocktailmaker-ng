package com.stonewashedpc.cocktailmakerng.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stonewashedpc.cocktailmakerng.entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

}
