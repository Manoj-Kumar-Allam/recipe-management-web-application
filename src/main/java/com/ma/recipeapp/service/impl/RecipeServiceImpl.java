package com.ma.recipeapp.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ma.recipeapp.model.Recipe;
import com.ma.recipeapp.repository.RecipeRepository;
import com.ma.recipeapp.service.RecipeService;

@Service
public class RecipeServiceImpl implements RecipeService {
	
	private final RecipeRepository recipeRepository;

	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipes = new HashSet<>();
		this.recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}
	
	
}
