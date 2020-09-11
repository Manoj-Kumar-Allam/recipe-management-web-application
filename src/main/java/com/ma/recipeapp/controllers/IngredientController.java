package com.ma.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ma.recipeapp.commands.IngredientCommand;
import com.ma.recipeapp.service.IngredientService;
import com.ma.recipeapp.service.RecipeService;
import com.ma.recipeapp.service.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {
	
	private final RecipeService recipeService;
	
	private final IngredientService ingredientService;
	
	private final UnitOfMeasureService unitOfMeasureService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService unitOfMeasureService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.debug("Getting Ingredients for the Recipe Id : "+ recipeId +"....");
		model.addAttribute("recipe", this.recipeService.findRecipeCommandById(Long.valueOf(recipeId)));
		return "recipe/ingredient/list";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		log.debug("Fecthing Ingredient with Id : " + ingredientId + " of Recipe with Id : "+ recipeId + "....");
		model.addAttribute("ingredient", this.ingredientService.findIngredientByIdAndRecipeId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		return "recipe/ingredient/show";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/new")
	public String addNewIngredient(@PathVariable String recipeId, Model model) {
		log.debug("Adding New Ingredient....");
		model.addAttribute("ingredient", this.ingredientService.getNewIngredientCommand(Long.valueOf(recipeId)));
		model.addAttribute("unitOfMeasureList", this.unitOfMeasureService.listAllUnitOfMeasures());
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		log.debug("Updating Ingredient with Id : " + ingredientId + " of Recipe with Id : "+ recipeId + "....");
		model.addAttribute("ingredient", this.ingredientService.findIngredientByIdAndRecipeId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		model.addAttribute("unitOfMeasureList", this.unitOfMeasureService.listAllUnitOfMeasures());
		return "recipe/ingredient/ingredientform";
	}
	
	@PostMapping("/recipe/{recipeId}/ingredient")
	public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
		log.debug("Saving Ingredient....");
		IngredientCommand saveIngredientCommand = this.ingredientService.saveIngredientCommand(ingredientCommand);
		return "redirect:/recipe/" + saveIngredientCommand.getRecipeId() + "/ingredient/" + saveIngredientCommand.getId() + "/show";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
	public String deleteRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
		log.debug("Deleting Ingredient with Id : " + ingredientId + " of Recipe with Id : "+ recipeId + "....");
		this.ingredientService.deleteIngredientByRecipeIdAndId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		return "redirect:/recipe/"+ recipeId + "/ingredients/";
	}
	
}
