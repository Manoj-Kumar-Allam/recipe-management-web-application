package com.ma.recipeapp.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import com.ma.recipeapp.model.Recipe;
import com.ma.recipeapp.repository.RecipeRepository;
import com.ma.recipeapp.service.ImageService;

public class ImageServiceImplTest {
	
	@Mock
	RecipeRepository recipeRepository;
	
	ImageService imageService;
	
	@Before
	public void setUp()
		throws Exception {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRepository);
	}
	
	@Test
	public void testSaveImageFile() throws Exception {
		Recipe r = new Recipe();
		r.setId(10l);
		
		Mockito.when(this.recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(r));
		
		MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "test.txt", "text/plain", "MANOJ".getBytes());
		
		this.imageService.saveImageFile(new Long(10l), multipartFile);
		
		ArgumentCaptor<Recipe> argumentCapture = ArgumentCaptor.forClass(Recipe.class);
		
		Mockito.verify(this.recipeRepository, Mockito.times(1)).save(argumentCapture.capture());
		
		Recipe value = argumentCapture.getValue();
		
		assertEquals(multipartFile.getBytes().length, value.getImage().length);
	}
	
}
