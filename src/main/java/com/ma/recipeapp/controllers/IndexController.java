package com.ma.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ma.recipeapp.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {
	
	private final RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@RequestMapping({"", "/", "/index"})
	public String getIndexPage(Model model) {
		log.debug("Fetching all the Recipes");
		model.addAttribute("recipes", this.recipeService.getRecipes());
		return "index";
	}
	
	@RequestMapping({"/recipe/show/{id}"})
	public String getRecipeById(@PathVariable String id, Model model) {
		log.debug("Fetching Recipe having ID : " + id);
		model.addAttribute("recipe", this.recipeService.findById(new Long(id)));
		return "recipe/show";
	}
}
