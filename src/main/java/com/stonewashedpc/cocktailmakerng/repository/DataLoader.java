package com.stonewashedpc.cocktailmakerng.repository;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.stonewashedpc.cocktailmakerng.entities.Cocktail;
import com.stonewashedpc.cocktailmakerng.entities.CocktailBuilder;
import com.stonewashedpc.cocktailmakerng.entities.Ingredient;
import com.stonewashedpc.cocktailmakerng.entities.Pump;
import com.stonewashedpc.cocktailmakerng.entities.step.Serving;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
	
	private CocktailRepository cocktailRepository;
	
	private PumpRepository pumpRepository;
	
	private IngredientRepository ingredientRepository;
	
	public DataLoader(CocktailRepository cocktailRepository, PumpRepository pumpRepository, IngredientRepository ingredientRepository) {
		this.cocktailRepository = cocktailRepository;
		this.pumpRepository = pumpRepository;
		this.ingredientRepository = ingredientRepository;
	}
	
	@PostConstruct
	private void loadData() {
		Ingredient i1 = new Ingredient("Tonic Water");
		Ingredient i2 = new Ingredient("Gin", 0.44);
		Ingredient i3 = new Ingredient("Cola");
		Ingredient i4 = new Ingredient("Wodka", 0.4);
		Ingredient i5 = new Ingredient("43er", 0.31);
		Ingredient i6 = new Ingredient("Milch");
		Ingredient i7 = new Ingredient("Mangosaft");
		
		ingredientRepository.saveAll(Set.of(i1, i2, i3, i4, i5, i6 , i7));
		
		try {
			Cocktail c1 = new CocktailBuilder("Gin Tonic").addManualStep("Eiswuerfel hinzufuegen.").addSequentialStep(List.of(new Serving(i2, 40), new Serving(i1, 200))).build();
			Cocktail c2 = new CocktailBuilder("Wodka-Cola").addManualStep("Eiswuerfel hinzufuegen.").addParallelStep(List.of(new Serving(i3, 200), new Serving(i4, 30))).build();
			Cocktail c3 = new CocktailBuilder("43er Milch").addSequentialStep(List.of(new Serving(i6, 200), new Serving(i5, 30))).build();
			Cocktail c4 = new CocktailBuilder("43er Mango").addParallelStep(List.of(new Serving(i7, 200), new Serving(i5, 30))).build();
			Cocktail c5 = new CocktailBuilder("Gin Pure").addSequentialStep(List.of(new Serving(i2, 30))).build();
			Cocktail c6 = new CocktailBuilder("43er Milch Mango").addParallelStep(List.of(new Serving(i6, 75), new Serving(i7, 125), new Serving(i5, 30))).build();
			
			cocktailRepository.saveAll(Set.of(c1, c2, c3, c4, c5, c6));
			
			for (int i = 0; i < 10000; i++) {
				Cocktail c = new CocktailBuilder("Cocktail " + i).addParallelStep(List.of(new Serving(i6, 75), new Serving(i7, 125), new Serving(i5, 30))).build();
				
				cocktailRepository.save(c);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pumpRepository.save(new Pump(18, 400, i1));
		pumpRepository.save(new Pump(27, 400, i2));
		pumpRepository.save(new Pump(22, 400, i3));
		pumpRepository.save(new Pump(23, 400, i5));
		pumpRepository.save(new Pump(24, 400, i7));
		pumpRepository.save(new Pump(25, 400, i4));
		pumpRepository.save(new Pump(4, 400, i4));
		pumpRepository.save(new Pump(17, 400, i1));
	}
}
