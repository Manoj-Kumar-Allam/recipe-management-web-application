package com.ma.recipeapp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ma.recipeapp.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{
	
	Optional<Category> findByDescription(String description);

}
