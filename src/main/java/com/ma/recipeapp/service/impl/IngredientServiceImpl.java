package com.ma.recipeapp.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ma.recipeapp.commands.IngredientCommand;
import com.ma.recipeapp.commands.UnitOfMeasureCommand;
import com.ma.recipeapp.converters.IngredientCommandToIngredient;
import com.ma.recipeapp.converters.IngredientToIngredientCommand;
import com.ma.recipeapp.model.Ingredient;
import com.ma.recipeapp.model.Recipe;
import com.ma.recipeapp.repository.reactive.RecipeReactiveRepository;
import com.ma.recipeapp.repository.reactive.UnitOfMeasureReactiveRepository;
import com.ma.recipeapp.service.IngredientService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeReactiveRepository recipeRepository;

	private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;

	private final IngredientToIngredientCommand ingredientToIngredientCommand;

	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	public IngredientServiceImpl(RecipeReactiveRepository recipeRepository,
			UnitOfMeasureReactiveRepository unitOfMeasureRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		super();
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public Mono<IngredientCommand> findIngredientByIdAndRecipeId(String recipeId, String ingredientId) {
//		return recipeRepository.findById(recipeId)
//				.map(recipe -> recipe.getIngredients()
//						.stream()
//						.filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
//						.findFirst())
//				.filter(Optional::isPresent)
//				.map(ingredient -> {
//					IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient.get());
//					ingredientCommand.setRecipeId(recipeId);
//					return ingredientCommand;
//				});
		return recipeRepository
				.findById(recipeId)
				.flatMapIterable(Recipe::getIngredients)
				.filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
				.single()
				.map(ingredient -> {
					IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
					ingredientCommand.setRecipeId(recipeId);
					return ingredientCommand;
				});
				
		
	}

	@Override
	public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {

		Recipe recipe = this.recipeRepository.findById(ingredientCommand.getRecipeId()).block();
		if (recipe != null) {
			Optional<Ingredient> ingredientById = recipe.getIngredients()
														.stream()
					                                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
					                                    .findFirst();
			if (ingredientById.isPresent()) {
				Ingredient existingIngredient = ingredientById.get();
				existingIngredient.setDescription(ingredientCommand.getDescription());
				existingIngredient.setAmount(ingredientCommand.getAmount());
				existingIngredient.setUnitOfMeasure(
						this.unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId()).block());
				if(existingIngredient.getUnitOfMeasure() == null) {
					throw new RuntimeException("Unit Of Measure Not Found");
				}
			} else {
				log.debug("Ingredient with Id : " + ingredientCommand.getId() + " Not found");
				Ingredient originaIngredient = this.ingredientCommandToIngredient.convert(ingredientCommand);
				recipe.addIngredient(originaIngredient);
			}

			Recipe savedRecipe = this.recipeRepository.save(recipe).block();

			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //check by description
            if (!savedIngredientOptional.isPresent()) {
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            //todo check for fail

            //enhance with id value
            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return Mono.just(ingredientCommandSaved);
		}
		return Mono.empty();
	}

	@Override
	public Mono<IngredientCommand> getNewIngredientCommand(String recipeId) {
		Recipe recipe = this.recipeRepository.findById(recipeId).block();
		if (recipe != null) {
			IngredientCommand ingredientCommand = new IngredientCommand();
			ingredientCommand.setRecipeId(recipeId);
			ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
			return Mono.just(ingredientCommand);
		} else {
			log.debug("Recipe with Id : " + recipeId + " Not found");
		}

		return Mono.empty();
	}

	@Override
	public Mono<Void> deleteIngredientByRecipeIdAndId(String recipeId, String ingredientId) {
		Recipe recipe = this.recipeRepository.findById(recipeId).block();
		if (recipe != null) {
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
		return Mono.empty();
	}

}
