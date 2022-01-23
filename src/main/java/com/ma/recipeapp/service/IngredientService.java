package com.ma.recipeapp.service;

import com.ma.recipeapp.commands.IngredientCommand;

import reactor.core.publisher.Mono;

public interface IngredientService {
		
	Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);
	
	Mono<IngredientCommand> getNewIngredientCommand(String recipeId);
	
	Mono<Void> deleteIngredientByRecipeIdAndId(String recipeId, String ingredientId);

	Mono<IngredientCommand> findIngredientByIdAndRecipeId(String recipeId, String ingredientId);
	
}
