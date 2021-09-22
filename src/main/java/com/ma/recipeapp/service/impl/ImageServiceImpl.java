package com.ma.recipeapp.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ma.recipeapp.model.Recipe;
import com.ma.recipeapp.repository.RecipeRepository;
import com.ma.recipeapp.service.ImageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
	
	private final RecipeRepository recipeRepository;
	
	
	public ImageServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}


	@Override
	public void saveImageFile(String recipeId, MultipartFile multipartFile) {
		log.debug("Saving Image file "  + multipartFile.getOriginalFilename() + "....");
		Optional<Recipe> recipeById = this.recipeRepository.findById(recipeId);
		
		if(recipeById.isPresent()) {
			Recipe recipe = recipeById.get();
			try {
				Byte[] bytearray = new Byte[multipartFile.getBytes().length];
				int i = 0;
				for (byte b : multipartFile.getBytes()) {
					bytearray[i++] = b;
				}
				
				recipe.setImage(bytearray);
				
				this.recipeRepository.save(recipe);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			log.debug("Recipe Not Found with Id : " + recipeId);
		}
	}

}
