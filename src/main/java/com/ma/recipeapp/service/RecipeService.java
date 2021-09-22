package com.ma.recipeapp.service;

import java.util.Set;

import com.ma.recipeapp.commands.RecipeCommand;
import com.ma.recipeapp.model.Recipe;

public interface RecipeService {
	
	public Set<Recipe> getRecipes();
	
	public Recipe findRecipeById(String id);
	
	public RecipeCommand findRecipeCommandById(String id);
	
	public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
	
	public void deleteRecipeById(String id);
}
