package com.ma.recipeapp.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ma.recipeapp.commands.IngredientCommand;
import com.ma.recipeapp.commands.RecipeCommand;
import com.ma.recipeapp.commands.UnitOfMeasureCommand;
import com.ma.recipeapp.service.IngredientService;
import com.ma.recipeapp.service.RecipeService;
import com.ma.recipeapp.service.UnitOfMeasureService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IngredientControllerTest {

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    RecipeService recipeService;

    IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findRecipeCommandById(Mockito.anyString())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService, times(1)).findRecipeCommandById(Mockito.anyString());
    }

    @Test
    public void testShowIngredient() throws Exception {
        //given
        Mono<IngredientCommand> ingredientCommand = Mono.just(new IngredientCommand());

        //when
        when(ingredientService.findIngredientByIdAndRecipeId(Mockito.anyString(), Mockito.anyString())).thenReturn(ingredientCommand);

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void testNewIngredientForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        //when
        when(recipeService.findRecipeCommandById(Mockito.anyString())).thenReturn(recipeCommand);
        when(unitOfMeasureService.listAllUnitOfMeasures()).thenReturn(Flux.just(new UnitOfMeasureCommand()));
        when(ingredientService.getNewIngredientCommand(Mockito.anyString())).thenReturn(Mono.just(new IngredientCommand()));

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitOfMeasureList"));

        verify(recipeService, times(1)).findRecipeCommandById(Mockito.anyString());

    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        //given
        Mono<IngredientCommand> ingredientCommand = Mono.just(new IngredientCommand());

        //when
        when(ingredientService.findIngredientByIdAndRecipeId(Mockito.anyString(), Mockito.anyString())).thenReturn(ingredientCommand);
        when(unitOfMeasureService.listAllUnitOfMeasures()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitOfMeasureList"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");
        

        //when
        when(ingredientService.saveIngredientCommand(Mockito.any())).thenReturn(Mono.just(command));

        //then
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

    }

    @Test
    public void testDeleteIngredient() throws Exception {
    	
    	when(ingredientService.deleteIngredientByRecipeIdAndId(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        //then
        mockMvc.perform(get("/recipe/2/ingredient/3/delete")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

        verify(ingredientService, times(1)).deleteIngredientByRecipeIdAndId(Mockito.anyString(), Mockito.anyString());

    }
}