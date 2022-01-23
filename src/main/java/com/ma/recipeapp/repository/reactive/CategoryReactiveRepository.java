package com.ma.recipeapp.repository.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.ma.recipeapp.model.Category;

import reactor.core.publisher.Mono;

public interface CategoryReactiveRepository extends ReactiveCrudRepository<Category, String> {
	
	Mono<Category> findByDescription(String description);
}
