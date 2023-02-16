package com.stonewashedpc.cocktailmakerng.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stonewashedpc.cocktailmakerng.entities.step.Step;

public interface StepRepository extends JpaRepository<Step, Long> {

}
