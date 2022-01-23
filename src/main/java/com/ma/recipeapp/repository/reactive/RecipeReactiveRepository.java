package com.ma.recipeapp.repository.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.ma.recipeapp.model.Recipe;

public interface RecipeReactiveRepository extends ReactiveCrudRepository<Recipe, String> {

}
