package com.ma.recipeapp.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ma.recipeapp.commands.RecipeCommand;
import com.ma.recipeapp.converters.RecipeCommandToRecipe;
import com.ma.recipeapp.converters.RecipeToRecipeCommand;
import com.ma.recipeapp.exceptions.NotFoundException;
import com.ma.recipeapp.model.Recipe;
import com.ma.recipeapp.repository.RecipeRepository;
import com.ma.recipeapp.service.RecipeService;

@Service
public class RecipeServiceImpl implements RecipeService {
	
	private final RecipeRepository recipeRepository;
	
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipes = new HashSet<>();
		this.recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}

	@Override
	public Recipe findRecipeById(Long id) {
		Optional<Recipe> recipeById = this.recipeRepository.findById(id);
		if(!recipeById.isPresent()) {
			throw new NotFoundException("Recipe Not Found. For ID : " + id.toString());
		}
		return recipeById.get();
	}
	
	@Override
	public RecipeCommand findRecipeCommandById(Long id) {
		return this.recipeToRecipeCommand.convert(findRecipeById(id));
	}

	@Transactional
	@Override
	public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
		Recipe detachedRecipe = this.recipeCommandToRecipe.convert(recipeCommand);
		return this.recipeToRecipeCommand.convert(this.recipeRepository.save(detachedRecipe));
	}

	@Override
	public void deleteRecipeById(Long id) {
		this.recipeRepository.deleteById(id);
	}

	
}
