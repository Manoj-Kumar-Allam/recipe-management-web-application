package com.ma.recipeapp.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ma.recipeapp.commands.IngredientCommand;
import com.ma.recipeapp.commands.UnitOfMeasureCommand;
import com.ma.recipeapp.converters.IngredientCommandToIngredient;
import com.ma.recipeapp.converters.IngredientToIngredientCommand;
import com.ma.recipeapp.model.Ingredient;
import com.ma.recipeapp.model.Recipe;
import com.ma.recipeapp.repository.RecipeRepository;
import com.ma.recipeapp.repository.UnitOfMeasureRepository;
import com.ma.recipeapp.service.IngredientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;

	private final UnitOfMeasureRepository unitOfMeasureRepository;

	private final IngredientToIngredientCommand ingredientToIngredientCommand;

	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public IngredientCommand findIngredientByIdAndRecipeId(String recipeId, String ingredientId) {
		Optional<Recipe> recipeById = this.recipeRepository.findById(recipeId);
		if (!recipeById.isPresent()) {
			log.debug("Recipe with Id : " + recipeId + " Not found");
		} else {
			Recipe recipe = recipeById.get();
			Optional<IngredientCommand> ingredientById = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientId))
					.map(this.ingredientToIngredientCommand::convert).findFirst();
			if (!ingredientById.isPresent()) {
				log.debug("Ingredient with Id : " + ingredientId + " Not found");
			} else {
				return ingredientById.get();
			}
		}
		return null;
	}

	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

		Optional<Recipe> recipeById = this.recipeRepository.findById(ingredientCommand.getRecipeId());
		if (recipeById.isPresent()) {
			Recipe recipe = recipeById.get();
			Optional<Ingredient> ingredientById = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
			if (ingredientById.isPresent()) {
				Ingredient existingIngredient = ingredientById.get();
				existingIngredient.setDescription(ingredientCommand.getDescription());
				existingIngredient.setAmount(ingredientCommand.getAmount());
				existingIngredient.setUnitOfMeasure(
						this.unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
								.orElseThrow(() -> new RuntimeException("Unit Of Measure Not Found")));
			} else {
				log.debug("Ingredient with Id : " + ingredientCommand.getId() + " Not found");
				Ingredient originaIngredient = this.ingredientCommandToIngredient.convert(ingredientCommand);
				recipe.addIngredient(originaIngredient);
			}

			Recipe savedRecipe = this.recipeRepository.save(recipe);

			if (ingredientCommand.getId() != null) {
				return savedRecipe.getIngredients().stream()
						.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
						.map(this.ingredientToIngredientCommand::convert).findFirst().orElse(null);
			} else {
				return savedRecipe.getIngredients().stream()
						.filter(ingredient -> ingredient.getDescription()
								.equalsIgnoreCase(ingredientCommand.getDescription()))
						.filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
						.filter(ingredient -> ingredient.getUnitOfMeasure().getId()
								.equals(ingredientCommand.getUnitOfMeasure().getId()))
						.map(this.ingredientToIngredientCommand::convert).findFirst().orElse(null);
			}

		} else {
			log.debug("Recipe with Id : " + ingredientCommand.getRecipeId() + " Not found");
		}
		return null;
	}

	@Override
	public IngredientCommand getNewIngredientCommand(String recipeId) {
		Optional<Recipe> recipeById = this.recipeRepository.findById(recipeId);
		if (recipeById.isPresent()) {
			IngredientCommand ingredientCommand = new IngredientCommand();
			ingredientCommand.setRecipeId(recipeId);
			ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
			return ingredientCommand;
		} else {
			log.debug("Recipe with Id : " + recipeId + " Not found");
		}

		return null;
	}

	@Override
	public void deleteIngredientByRecipeIdAndId(String recipeId, String ingredientId) {
		Optional<Recipe> recipeById = this.recipeRepository.findById(recipeId);
		if (recipeById.isPresent()) {
			Recipe recipe = recipeById.get();
			Optional<Ingredient> ingredientById = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();

			if (ingredientById.isPresent()) {
				log.debug("Ingredient Found");
				Ingredient ingredient = ingredientById.get();
				recipe.getIngredients().remove(ingredient);
				this.recipeRepository.save(recipe);
			}

		} else {
			log.debug("Recipe with Id : " + recipeId + " Not found");
		}
	}

}
