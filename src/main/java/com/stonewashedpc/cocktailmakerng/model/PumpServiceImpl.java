package com.stonewashedpc.cocktailmakerng.model;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stonewashedpc.cocktailmakerng.entities.Ingredient;
import com.stonewashedpc.cocktailmakerng.entities.Pump;
import com.stonewashedpc.cocktailmakerng.entities.step.PumpControlStepVisitor;
import com.stonewashedpc.cocktailmakerng.entities.step.Step;
import com.stonewashedpc.cocktailmakerng.exceptions.ElementNotFoundException;
import com.stonewashedpc.cocktailmakerng.exceptions.PumpControlException;
import com.stonewashedpc.cocktailmakerng.repository.PumpRepository;

@Service
public class PumpServiceImpl implements PumpService {

	private PumpRepository pumpRepository;
	
	private StepService stepService;
	
	private Semaphore mutex = new Semaphore(1);
	
	public PumpServiceImpl(PumpRepository pumpRepository, StepService stepService) {
		super();
		this.pumpRepository = pumpRepository;
		this.stepService = stepService;
	}

	@Override
	public List<Pump> findAll() {
		return pumpRepository.findAll();
	}

	@Override
	public Pump findById(Integer id) throws ElementNotFoundException {
		return pumpRepository.findById(id).orElseThrow(ElementNotFoundException::new);
	}
	
	@Override
	public Set<Pump> findByIngredient(Ingredient ingredient) throws ElementNotFoundException {
		Set<Pump> pumps = pumpRepository.findByIngredient(ingredient);
		if (pumps.isEmpty()) throw new ElementNotFoundException();
		return pumps;
	}

	@Override
	public Page<Pump> findPageable(Pageable page) {
		return pumpRepository.findAll(page);
	}

	@Override
	public void deleteById(Integer id) throws ElementNotFoundException {
		if (pumpRepository.existsById(id)) {
			pumpRepository.deleteById(id);
		} else throw new ElementNotFoundException();
	}

	@Override
	public Pump save(Pump object) {
		return pumpRepository.save(object);
	}

	@Override
	public boolean existsById(Integer id) {
		return pumpRepository.existsById(id);
	}

	@Override
	public long handleStepById(Long id) throws ElementNotFoundException, PumpControlException {
		Step step = stepService.findById(id);
		PumpControlStepVisitor visitor = new PumpControlStepVisitor(this);
		step.accept(visitor);
		return visitor.tryGetResult();
	}

	@Override
	public Set<Ingredient> findAvailableIngredients() {
		return this.findAll().stream().map(p -> p.getIngredient()).collect(Collectors.toSet());
	}
	
	@Override
	public void tryAcquireMutex() throws PumpControlException {
		if (!this.mutex.tryAcquire()) throw new PumpControlException("Access to pump service is mutually exclusive.");
	}
	
	@Override
	public void releaseMutex() {
		this.mutex.release();
	}
}
