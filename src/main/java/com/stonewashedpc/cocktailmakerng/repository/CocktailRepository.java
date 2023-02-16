package com.stonewashedpc.cocktailmakerng.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stonewashedpc.cocktailmakerng.entities.Cocktail;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
	
}
