package com.ma.recipeapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ma.recipeapp.model.Category;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeAppApplicationTests {  
	
	Category category;
	
	@Before
	public void setUp()
		throws Exception {
		category = new Category();
	}
	
	@Test
	public void getId() throws Exception {
		Long id = 4l;
		this.category.setId(id);
		assertEquals(id, this.category.getId());
	}
	

}
