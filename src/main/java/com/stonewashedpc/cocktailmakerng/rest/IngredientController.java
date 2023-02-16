package com.stonewashedpc.cocktailmakerng.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stonewashedpc.cocktailmakerng.entities.Ingredient;
import com.stonewashedpc.cocktailmakerng.exceptions.ElementNotFoundException;
import com.stonewashedpc.cocktailmakerng.model.IngredientServiceImpl;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
	
	private IngredientServiceImpl ingredientService;

	public IngredientController(IngredientServiceImpl ingredientService) {
		super();
		this.ingredientService = ingredientService;
	}
	
	@GetMapping
	public Page<Ingredient> getIngredients(Pageable page) {
		return ingredientService.findPageable(page);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getIngredient(@PathVariable Long id) {
		try {
			return new ResponseEntity<Ingredient>(ingredientService.findById(id), HttpStatus.OK);
		} catch (ElementNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<?> postIngredient(@RequestBody Ingredient ingredient) {
		return new ResponseEntity<Ingredient>(ingredientService.save(ingredient), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> putIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
		return ingredientService.existsById(id) ? 
				new ResponseEntity<Ingredient>(ingredientService.save(ingredient), HttpStatus.OK)
				: new ResponseEntity<Ingredient>(ingredientService.save(ingredient), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIngredient(@PathVariable Long id) {
		try {
			ingredientService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (ElementNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
