package com.stonewashedpc.cocktailmakerng.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stonewashedpc.cocktailmakerng.exceptions.ElementNotFoundException;

public interface Service<T, ID> {
	
	public List<T> findAll();
	public T findById(ID id) throws ElementNotFoundException;
	public Page<T> findPageable(Pageable page);
	public void deleteById(ID id) throws ElementNotFoundException;
	public T save(T object);
	public boolean existsById(ID id);
}
