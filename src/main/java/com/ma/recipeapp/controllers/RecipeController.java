package com.ma.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ma.recipeapp.commands.RecipeCommand;
import com.ma.recipeapp.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {
	
	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	
	@GetMapping({"/recipe/{id}/show"})
	public String getRecipeById(@PathVariable String id, Model model) {
		log.debug("Fetching Recipe having ID : " + id);
		model.addAttribute("recipe", this.recipeService.findRecipeById(new Long(id)));
		return "recipe/show";
	}
	
	@GetMapping("/recipe/new")
	public String addNewRecipe(Model model) {
		log.debug("Adding New Recipe...");
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	@GetMapping("/recipe/{id}/update")
	public String updateRecipeById(@PathVariable String id, Model model) {
		log.debug("Updating Recipe with Id : " + id + ".......");
		model.addAttribute("recipe", this.recipeService.findRecipeCommandById(new Long(id)));
		return "recipe/recipeform";
	}
	
	@PostMapping("/recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
		log.debug("Saving Recipe....");
		RecipeCommand saveRecipeCommand = this.recipeService.saveRecipeCommand(recipeCommand);
		return "redirect:/recipe/" + saveRecipeCommand.getId() +"/show";
	}
	
	@GetMapping("/recipe/{id}/delete")
	public String deleteRecipeById(@PathVariable String id) {
		log.debug("Deleting Recipe with Id : " + id + ".......");
		this.recipeService.deleteRecipeById(Long.valueOf(id));
		return "redirect:/";
	}
}
