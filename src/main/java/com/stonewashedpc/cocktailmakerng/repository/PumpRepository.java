package com.stonewashedpc.cocktailmakerng.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stonewashedpc.cocktailmakerng.entities.Ingredient;
import com.stonewashedpc.cocktailmakerng.entities.Pump;

public interface PumpRepository extends JpaRepository<Pump, Integer> {
		public Set<Pump> findByIngredient(Ingredient ingredient);
}
