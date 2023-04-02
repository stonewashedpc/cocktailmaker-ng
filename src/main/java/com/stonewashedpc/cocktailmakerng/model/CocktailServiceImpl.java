package com.stonewashedpc.cocktailmakerng.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stonewashedpc.cocktailmakerng.entities.Cocktail;
import com.stonewashedpc.cocktailmakerng.entities.Ingredient;
import com.stonewashedpc.cocktailmakerng.entities.step.IngredientCollectionStepVisitor;
import com.stonewashedpc.cocktailmakerng.exceptions.ElementNotFoundException;
import com.stonewashedpc.cocktailmakerng.repository.CocktailRepository;

@Service
public class CocktailServiceImpl implements CocktailService<Cocktail, Long> {

	private CocktailRepository cocktailRepository;
	
	private PumpService pumpService;
	
	private Optional<List<Cocktail>> possibleCocktailsCache;
	
	public CocktailServiceImpl(CocktailRepository cocktailRepository, PumpService pumpService) {
		this.cocktailRepository = cocktailRepository;
		this.pumpService = pumpService;
		this.possibleCocktailsCache = Optional.empty();
	}
	
	@Override
	public List<Cocktail> findAll() {
		return cocktailRepository.findAll();
	}

	@Override
	public Cocktail findById(Long id) throws ElementNotFoundException {
		return cocktailRepository.findById(id).orElseThrow(ElementNotFoundException::new);
	}

	@Override
	public Page<Cocktail> findPageable(Pageable page) {
		return cocktailRepository.findAll(page);
	}

	@Override
	public void deleteById(Long id) throws ElementNotFoundException {
		if (cocktailRepository.existsById(id)) {
			cocktailRepository.deleteById(id);
			this.clearCache();
		} else throw new ElementNotFoundException();
	}

	@Override
	public Cocktail save(Cocktail object) {
		Cocktail c = cocktailRepository.save(object);
		this.clearCache();
		return c;
	}

	@Override
	public boolean existsById(Long id) {
		return cocktailRepository.existsById(id);
	}
	
	// TODO Optimize Performance (use native sql in repository?)
	@Override
	public List<Cocktail> findPossible() {
		if (this.possibleCocktailsCache.isEmpty()) {
			System.out.println("Recomputing cache...");
			IngredientCollectionStepVisitor visitor = new IngredientCollectionStepVisitor();
			Set<Ingredient> availableIngredients = this.pumpService.findAvailableIngredients();
			
			this.possibleCocktailsCache = Optional.of(cocktailRepository.findAll().stream().filter(c -> {
				return c.getSteps().stream().map(s -> {
					s.accept(visitor);
					return visitor.getResult();
				}).flatMap(Set::stream).allMatch(availableIngredients::contains);
				
			}).collect(Collectors.toList()));
			
		}
		return this.possibleCocktailsCache.get();
	}
	
	private <T> Page<T> asPage(List<T> list, Pageable page) {
		int totalSize = list.size();
		int start = (int) Math.min(page.getOffset(), totalSize);
		int end = Math.min(start + page.getPageSize(), totalSize);
		return new PageImpl<T>(list.subList(start, end), page, totalSize);
	}
	
	
	@Override
	public Page<Cocktail> findPossiblePageable(Pageable page) {
		return this.asPage(this.findPossible(), page);
	}

	@Override
	public void clearCache() {
		this.possibleCocktailsCache = Optional.empty();
	}

	@Override
	public Page<Cocktail> findPossiblePageableByName(String name, Pageable page) {
		return this.asPage(this.findPossible().stream()
					.filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
					.collect(Collectors.toList())
				, page);
	}

	@Override
	public Page<Cocktail> findPageableByName(String name, Pageable page) {
		return this.asPage(this.findAll().stream()
					.filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
					.collect(Collectors.toList())
				, page);
	}

}
