package com.ma.recipeapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	
	void saveImageFile(String recipeId, MultipartFile multipartFile);
}
