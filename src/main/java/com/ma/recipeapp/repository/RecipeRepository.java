package com.ma.recipeapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.ma.recipeapp.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{

}
