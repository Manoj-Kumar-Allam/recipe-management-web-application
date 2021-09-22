package com.ma.recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.ma.recipeapp.model.Recipe;
import com.ma.recipeapp.service.RecipeService;

import junit.framework.TestCase;

/**
 * The class <code>IndexControllerTest</code> contains tests for the class
 * <code>{@link IndexController}</code>.
 *
 * @generatedBy CodePro at 9/7/20 6:16 PM, using the Spring generator
 * @author manojkumara
 * @version $Revision: 1.0 $
 */
public class IndexControllerTest {
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	IndexController controller;
	
	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception if the initialization fails for some reason
	 *
	 * @see TestCase#setUp()
	 *
	 * @generatedBy CodePro at 9/7/20 6:16 PM
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		this.controller = new IndexController(recipeService); 
	}
	
	@Test
	public void getIndexPage() {
		String indexPage = this.controller.getIndexPage(this.model);
		assertEquals("index", indexPage);
		Mockito.verify(this.recipeService, Mockito.times(1)).getRecipes();
		Mockito.verify(this.model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"), Mockito.anySet());
	}
	
	@Test
	public void getIndexPage_1() {
		
		Set<Recipe> recipes = new HashSet<>();
		
		Recipe recipe = new Recipe();
		recipe.setId("1");
		
		Recipe recipe1 = new Recipe();
		recipe1.setId("10");
		
		recipes.add(recipe);
		recipes.add(recipe1);
		
		Mockito.when(this.recipeService.getRecipes()).thenReturn(recipes);
		
		@SuppressWarnings("unchecked")
		ArgumentCaptor<Set<Recipe>> argumentCapture = ArgumentCaptor.forClass(Set.class);
		
		String indexPage = this.controller.getIndexPage(this.model);
		assertEquals("index", indexPage);
		
		Mockito.verify(this.recipeService, Mockito.times(1)).getRecipes();
		Mockito.verify(this.model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"), argumentCapture.capture());
		Set<Recipe> allValues = argumentCapture.getValue();
		assertEquals(2, allValues.size());
		
	}
	
	@Test
	public void testMockMVC() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("index"));
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

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 9/7/20 6:16 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(IndexControllerTest.class);
	}
}