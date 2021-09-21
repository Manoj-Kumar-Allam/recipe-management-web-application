package com.ma.recipeapp.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.ma.recipeapp.commands.RecipeCommand;
import com.ma.recipeapp.exceptions.NotFoundException;
import com.ma.recipeapp.model.Recipe;
import com.ma.recipeapp.service.RecipeService;

import junit.framework.TestCase;

public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	RecipeController controller;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		this.controller = new RecipeController(recipeService); 
	}
	
	@Test
	public void getRecipeById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1l);
		
		Mockito.when(this.recipeService.findRecipeById(Mockito.anyLong())).thenReturn(recipe);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("recipe/show")).andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
	}
	
	@Test
	public void getRecipeByIdNotFound() throws Exception {		
		Mockito.when(this.recipeService.findRecipeById(Mockito.anyLong())).thenThrow(NotFoundException.class);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).setControllerAdvice(new ControllerExcpetionHandler()).build();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testPostAddNewRecipe() throws Exception {
		RecipeCommand cmd = new RecipeCommand();
		cmd.setId(2l);
		
		Mockito.when(this.recipeService.saveRecipeCommand(Mockito.any(RecipeCommand.class))).thenReturn(cmd);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
		
		mockMvc.perform(MockMvcRequestBuilders.post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "").param("description", "some String").param("preparationTime", "10").param("directions", "directions")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/show"));
	}
	
	@Test
	public void testUpdateRecipe() throws Exception{
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(2l);
		
		Mockito.when(this.recipeService.findRecipeCommandById(Mockito.anyLong())).thenReturn(recipeCommand);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/2/update")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("recipe/recipeform")).andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
		
		Mockito.verify(this.recipeService, Mockito.times(1)).findRecipeCommandById(Mockito.anyLong());
		
	}
	
	@Test
	public void testDeleteById() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/2/delete")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/"));
		Mockito.verify(this.recipeService, Mockito.times(1)).deleteRecipeById(Mockito.anyLong());
	}
	
	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception if the clean-up fails for some reason
	 *
	 * @see TestCase#tearDown()
	 *
	 * @generatedBy CodePro at 9/7/20 6:16 PM
	 */
	@After
	public void tearDown() throws Exception {
	}
	
}
