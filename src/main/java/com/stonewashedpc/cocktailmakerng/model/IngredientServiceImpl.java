package com.stonewashedpc.cocktailmakerng.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stonewashedpc.cocktailmakerng.entities.Ingredient;
import com.stonewashedpc.cocktailmakerng.exceptions.ElementNotFoundException;
import com.stonewashedpc.cocktailmakerng.repository.IngredientRepository;

@Service
public class IngredientServiceImpl implements IngredientService {
	
	private IngredientRepository ingredientRepository;
	
	public IngredientServiceImpl(IngredientRepository ingredientRepository) {
		super();
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	public List<Ingredient> findAll() {
		return ingredientRepository.findAll();
	}

	@Override
	public Ingredient findById(Long id) throws ElementNotFoundException {
		return ingredientRepository.findById(id).orElseThrow(ElementNotFoundException::new);
	}

	@Override
	public Page<Ingredient> findPageable(Pageable page) {
		return ingredientRepository.findAll(page);
	}

	@Override
	public void deleteById(Long id) throws ElementNotFoundException {
		if (ingredientRepository.existsById(id)) {
			ingredientRepository.deleteById(id);
		} else throw new ElementNotFoundException();
	}

	@Override
	public Ingredient save(Ingredient object) {
		return ingredientRepository.save(object);
	}

	@Override
	public boolean existsById(Long id) {
		return ingredientRepository.existsById(id);
	}

}
