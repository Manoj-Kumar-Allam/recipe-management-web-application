package com.ma.recipeapp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ma.recipeapp.model.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {
	
	Optional<UnitOfMeasure> findByDescription(String description);
}
