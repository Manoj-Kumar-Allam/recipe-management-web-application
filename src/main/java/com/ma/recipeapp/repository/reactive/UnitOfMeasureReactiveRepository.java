package com.ma.recipeapp.repository.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.ma.recipeapp.model.UnitOfMeasure;

import reactor.core.publisher.Mono;

public interface UnitOfMeasureReactiveRepository extends ReactiveCrudRepository<UnitOfMeasure, String> {
	
	Mono<UnitOfMeasure> findByDescription(String description);
}	
