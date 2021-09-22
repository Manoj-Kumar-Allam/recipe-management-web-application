package com.ma.recipeapp.service;

import com.ma.recipeapp.commands.IngredientCommand;

public interface IngredientService {
		
	IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
	
	IngredientCommand getNewIngredientCommand(String recipeId);
	
	void deleteIngredientByRecipeIdAndId(String recipeId, String ingredientId);

	IngredientCommand findIngredientByIdAndRecipeId(String recipeId, String ingredientId);
	
}
