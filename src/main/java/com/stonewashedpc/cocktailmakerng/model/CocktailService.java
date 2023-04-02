package com.stonewashedpc.cocktailmakerng.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CocktailService<T, ID> extends Service<T, ID> {
	
	public List<T> findPossible();
	public Page<T> findPossiblePageable(Pageable page);
	public Page<T> findPossiblePageableByName(String name, Pageable page);
	public Page<T> findPageableByName(String name, Pageable page);
	public void clearCache();
}
