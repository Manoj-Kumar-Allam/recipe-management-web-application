package com.ma.recipeapp.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ma.recipeapp.model.Recipe;
import com.ma.recipeapp.repository.reactive.RecipeReactiveRepository;
import com.ma.recipeapp.service.ImageService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
	
	private final RecipeReactiveRepository recipeRepository;
	
	
	public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}


	@Override
	public Mono<Void> saveImageFile(String recipeId, MultipartFile multipartFile) {
		log.debug("Saving Image file "  + multipartFile.getOriginalFilename() + "....");
		Mono<Recipe> recipeMono = recipeRepository.findById(recipeId)
                .map(recipe -> {
                    Byte[] byteObjects = new Byte[0];
                    try {
                        byteObjects = new Byte[multipartFile.getBytes().length];

                        int i = 0;

                        for (byte b : multipartFile.getBytes()) {
                            byteObjects[i++] = b;
                        }

                        recipe.setImage(byteObjects);

                        return recipe;

                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });

		recipeRepository.save(recipeMono.block()).block();

        return Mono.empty();
	}

}
