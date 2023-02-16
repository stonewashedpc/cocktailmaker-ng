package com.stonewashedpc.cocktailmakerng.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CocktailService<T, ID> extends Service<T, ID> {
	
	public Page<T> findPossiblePageable(Pageable page);
	public void clearCache();
}
