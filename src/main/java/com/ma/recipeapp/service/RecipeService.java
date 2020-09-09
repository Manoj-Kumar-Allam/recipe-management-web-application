package com.ma.recipeapp.service;

import java.util.Set;

import com.ma.recipeapp.commands.RecipeCommand;
import com.ma.recipeapp.model.Recipe;

public interface RecipeService {
	
	public Set<Recipe> getRecipes();
	
	public Recipe findById(Long id);
	
	public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
}
