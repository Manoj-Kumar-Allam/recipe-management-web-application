package com.ma.recipeapp.service;

import com.ma.recipeapp.commands.RecipeCommand;
import com.ma.recipeapp.model.Recipe;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {
	
	public Flux<Recipe> getRecipes();
	
	public Mono<Recipe> findRecipeById(String id);
	
	public Mono<RecipeCommand> findRecipeCommandById(String id);
	
	public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand);
	
	public Mono<Void> deleteRecipeById(String id);
}
