package com.ma.recipeapp.service.impl;

import org.springframework.stereotype.Service;

import com.ma.recipeapp.commands.RecipeCommand;
import com.ma.recipeapp.converters.RecipeCommandToRecipe;
import com.ma.recipeapp.converters.RecipeToRecipeCommand;
import com.ma.recipeapp.exceptions.NotFoundException;
import com.ma.recipeapp.model.Recipe;
import com.ma.recipeapp.repository.reactive.RecipeReactiveRepository;
import com.ma.recipeapp.service.RecipeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RecipeServiceImpl implements RecipeService {
	
	private final RecipeReactiveRepository recipeRepository;
	
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Flux<Recipe> getRecipes() {
		return this.recipeRepository.findAll();
	}

	@Override
	public Mono<Recipe> findRecipeById(String id) {
		Recipe recipeById = this.recipeRepository.findById(id).block();
		if(recipeById == null) {
			throw new NotFoundException("Recipe Not Found. For ID : " + id.toString());
		}
		return Mono.just(recipeById);
	}
	
	@Override
	public Mono<RecipeCommand> findRecipeCommandById(String id) {
		return findRecipeById(id).map(recipeToRecipeCommand::convert);
	}

	@Override
	public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {
		return recipeRepository.save(recipeCommandToRecipe.convert(recipeCommand)).map(recipeToRecipeCommand::convert);
	}

	@Override
	public Mono<Void> deleteRecipeById(String id) {
		recipeRepository.deleteById(id).block();
		return Mono.empty();
	}

	
}
