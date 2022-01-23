package com.ma.recipeapp.repository.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ma.recipeapp.model.Recipe;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {
	
	@Autowired
	RecipeReactiveRepository reactiveRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRecipeSave() {
		Recipe recipe = new Recipe();
		recipe.setDescription("New Recipe");
		
		reactiveRepository.save(recipe).block();
		
		Long count = reactiveRepository.count().block();
		assertEquals(Long.valueOf(1l), count);
	}

}
