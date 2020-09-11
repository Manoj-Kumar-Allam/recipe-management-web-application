package com.ma.recipeapp.service;

import com.ma.recipeapp.commands.IngredientCommand;

public interface IngredientService {
	
	IngredientCommand findIngredientByIdAndRecipeId(Long recipeId, Long ingredientId);
	
	IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
	
	IngredientCommand getNewIngredientCommand(Long recipeId);
	
	void deleteIngredientByRecipeIdAndId(Long recipeId, Long ingredientId);
	
}
