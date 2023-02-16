package com.stonewashedpc.cocktailmakerng.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stonewashedpc.cocktailmakerng.entities.step.Step;
import com.stonewashedpc.cocktailmakerng.exceptions.ElementNotFoundException;
import com.stonewashedpc.cocktailmakerng.repository.StepRepository;

@Service
public class StepServiceImpl implements StepService {
	
	private StepRepository stepRepository;

	public StepServiceImpl(StepRepository stepRepository) {
		super();
		this.stepRepository = stepRepository;
	}

	@Override
	public List<Step> findAll() {
		return stepRepository.findAll();
	}

	@Override
	public Step findById(Long id) throws ElementNotFoundException {
		return stepRepository.findById(id).orElseThrow(ElementNotFoundException::new);
	}

	@Override
	public Page<Step> findPageable(Pageable page) {
		return stepRepository.findAll(page);
	}

	@Override
	public void deleteById(Long id) throws ElementNotFoundException {
		if (stepRepository.existsById(id)) {
			stepRepository.deleteById(id);
		} else throw new ElementNotFoundException();
	}

	@Override
	public Step save(Step object) {
		return stepRepository.save(object);
	}

	@Override
	public boolean existsById(Long id) {
		return stepRepository.existsById(id);
	}

}
