package com.stonewashedpc.cocktailmakerng.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stonewashedpc.cocktailmakerng.entities.Pump;
import com.stonewashedpc.cocktailmakerng.exceptions.ElementNotFoundException;
import com.stonewashedpc.cocktailmakerng.exceptions.PumpControlException;
import com.stonewashedpc.cocktailmakerng.model.PumpServiceImpl;

@RestController
@RequestMapping("/api/pumps")
public class PumpController {
	
	private PumpServiceImpl pumpService;

	public PumpController(PumpServiceImpl pumpRepository) {
		super();
		this.pumpService = pumpRepository;
	}
	
	@GetMapping
	public Page<Pump> getPumps(@NonNull Pageable pageable) {
		return pumpService.findPageable(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPump(@PathVariable Integer id) {
		try {
			return new ResponseEntity<Pump>(pumpService.findById(id), HttpStatus.OK);
		} catch (ElementNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<?> postPump(@RequestBody Pump pump) {
		return new ResponseEntity<Pump>(pumpService.save(pump), HttpStatus.OK);
	}
	
	@PostMapping("/handlestep/{id}")
	public ResponseEntity<?> handleStepById(@PathVariable Long id) {
		try {
			Long millis = pumpService.handleStepById(id);
			return ResponseEntity.ok(millis);
		} catch (ElementNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (PumpControlException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> putPump(@PathVariable Integer id, @RequestBody Pump pump) {
		return pumpService.existsById(id) ? 
				new ResponseEntity<Pump>(pumpService.save(pump), HttpStatus.OK)
				: new ResponseEntity<Pump>(pumpService.save(pump), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePump(@PathVariable Integer id) {
		try {
			pumpService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (ElementNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
