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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stonewashedpc.cocktailmakerng.entities.Cocktail;
import com.stonewashedpc.cocktailmakerng.exceptions.ElementNotFoundException;
import com.stonewashedpc.cocktailmakerng.model.CocktailServiceImpl;

@RestController
@RequestMapping("/api/cocktails")
public class CocktailController {
	
	private CocktailServiceImpl cocktailService;
	
	public CocktailController(CocktailServiceImpl cocktailService) {
		super();
		this.cocktailService = cocktailService;
	}

	@GetMapping
	public Page<Cocktail> getCocktailsByName(@RequestParam(required=false) String name, Pageable page) {
		return cocktailService.findPageableByName(name == null ? "" : name, page);
	}
	
	@GetMapping("/possible")
	public Page<Cocktail> getPossibleCocktailsByName(@RequestParam(required=false) String name, Pageable page) {
		return cocktailService.findPossiblePageableByName(name == null ? "" : name, page);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCocktail(@PathVariable Long id) {
		try {
			return new ResponseEntity<Cocktail>(cocktailService.findById(id), HttpStatus.OK);
		} catch(ElementNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<?> postCocktail(@RequestBody Cocktail cocktail) {
		return new ResponseEntity<Cocktail>(cocktailService.save(cocktail), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> putCocktail(@PathVariable Long id, @RequestBody Cocktail cocktail) {
		return cocktailService.existsById(id) ? 
				new ResponseEntity<Cocktail>(cocktailService.save(cocktail), HttpStatus.OK)
				: new ResponseEntity<Cocktail>(cocktailService.save(cocktail), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCocktail(@PathVariable Long id) {
		try {
			cocktailService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (ElementNotFoundException e) {
			
		}
		return ResponseEntity.notFound().build();
	}
	
//	 @PostMapping("/mix/{id}")
//	 public ResponseEntity<?> mixCocktail(@PathVariable Long id) {
//		 try {
//			Cocktail cocktail = cocktailService.findById(id);
//			cocktailService.mix(cocktail);
//			return ResponseEntity.noContent().build();
//		} catch (ElementNotFoundException e) {
//			return ResponseEntity.notFound().build();
//		}
//	 }
}
