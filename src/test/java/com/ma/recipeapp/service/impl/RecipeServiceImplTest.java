package com.ma.recipeapp.service.impl;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ma.recipeapp.converters.RecipeCommandToRecipe;
import com.ma.recipeapp.converters.RecipeToRecipeCommand;
import com.ma.recipeapp.model.Recipe;
import com.ma.recipeapp.repository.RecipeRepository;
import com.ma.recipeapp.service.impl.RecipeServiceImpl;

/**
 * The class <code>RecipeServiceImplTest</code> contains tests for the class <code>{@link RecipeServiceImpl}</code>.
 *
 * @generatedBy CodePro at 9/7/20 5:53 PM
 * @author manojkumara
 * @version $Revision: 1.0 $
 */
public class RecipeServiceImplTest {
	
	RecipeServiceImpl recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 9/7/20 5:53 PM
	 */
	@Before
	public void setUp()
		throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	/**
	 * Run the Set<Recipe> getRecipes() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 9/7/20 5:53 PM
	 */
	@Test
	public void testGetRecipes_1()
		throws Exception {
		Set<Recipe> result = this.recipeService.getRecipes();
		assertNotNull(result);
	}
	
	/**
	 * Run the Set<Recipe> getRecipes() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 9/7/20 5:53 PM
	 */
	@Test
	public void testGetRecipes_2()
		throws Exception {
		Recipe recipe = new Recipe();
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(recipe);
		Mockito.when(this.recipeRepository.findAll()).thenReturn(recipes);
		Set<Recipe> result = this.recipeService.getRecipes();
		assertEquals(1, result.size());
	}

	/**
	 * Run the Set<Recipe> getRecipes() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 9/7/20 5:53 PM
	 */
	@Test
	public void testGetRecipes_3()
		throws Exception {
		Recipe recipe = new Recipe();
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(recipe);
		Mockito.when(this.recipeRepository.findAll()).thenReturn(recipes);
		Set<Recipe> result = this.recipeService.getRecipes();
		Mockito.verify(this.recipeRepository, Mockito.times(1)).findAll();
		assertEquals(1, result.size());
	}
	
	@Test
	public void findRecipeById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(2l);
		Optional<Recipe> ops = Optional.of(recipe);
		Mockito.when(this.recipeRepository.findById(Mockito.anyLong())).thenReturn(ops);
		Recipe result = this.recipeService.findRecipeById(1l);
		assertNotNull(result);
		Mockito.verify(this.recipeRepository, Mockito.times(1)).findById(Mockito.anyLong());
		Mockito.verify(this.recipeRepository, Mockito.never()).findAll();
	}
	
	@Test
	public void testDeleteRecipeById() throws Exception {
		
		this.recipeService.deleteRecipeById(new Long(1l));
		
		Mockito.verify(this.recipeRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 9/7/20 5:53 PM
	 */
	@After
	public void tearDown()
		throws Exception {
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 9/7/20 5:53 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(RecipeServiceImplTest.class);
	}
}