package com.ma.recipeapp.controllers;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ma.recipeapp.commands.RecipeCommand;
import com.ma.recipeapp.service.ImageService;
import com.ma.recipeapp.service.RecipeService;

public class ImageControllerTest {

	@Mock
	ImageService imageService;
	
	@Mock
	RecipeService recipeService;
	
	ImageController controller;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		controller = new ImageController(imageService, recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(this.controller).setControllerAdvice(new ControllerExcpetionHandler()).build();
	}
	
	@Test
	public void getImageForm() throws Exception {
		RecipeCommand cmd = new RecipeCommand();
		cmd.setId(30l);
		Mockito.when(this.recipeService.findRecipeCommandById(Mockito.anyLong())).thenReturn(cmd);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/30/image")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("recipe/imageuploadform"));
		Mockito.verify(this.recipeService, Mockito.times(1)).findRecipeCommandById(Mockito.anyLong());
	}
	
	@Test
	public void handleImagePost() throws Exception {
		MockMultipartFile multiprtFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "testing.txt".getBytes());
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/recipe/30/image").file(multiprtFile)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(header().string("Location", "/recipe/30/show"));
		Mockito.verify(this.imageService, Mockito.times(1)).saveImageFile(Mockito.anyLong(), Mockito.any());
	}
	
	public void renderImageFromDBTest() throws Exception {
		RecipeCommand r = new RecipeCommand();
		r.setId(10l);
		
		String name = "I am Manoj";
		
		Byte[] b = new Byte[name.getBytes().length];
		
		int i = 0;
		for(byte b1 : name.getBytes()) {
			b[i++] = b1;
		}
		
		r.setImage(b);
		
		Mockito.when(this.recipeService.findRecipeCommandById(Mockito.anyLong())).thenReturn(r);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
		
		MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/recipe/10/recipeimage")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();
		
		byte[] contentAsByteArray = response.getContentAsByteArray();
		
		assertEquals(b.length, contentAsByteArray.length);

	}
	
	@After
	public void tearDown() throws Exception {
		
	}
}
