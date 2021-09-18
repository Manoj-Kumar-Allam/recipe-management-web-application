package com.ma.recipeapp.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ma.recipeapp.commands.RecipeCommand;
import com.ma.recipeapp.service.ImageService;
import com.ma.recipeapp.service.RecipeService;

@Controller
public class ImageController {
	
	private final ImageService imageService;
	
	private final RecipeService recipeService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		super();
		this.imageService = imageService;
		this.recipeService = recipeService;
	}
	
	@GetMapping("/recipe/{recipeId}/image")
	public String showUploadForm(@PathVariable String recipeId, Model model) {
		model.addAttribute("recipe", this.recipeService.findRecipeCommandById(Long.valueOf(recipeId)));
		return "recipe/imageuploadform";
	}
	
	@PostMapping("/recipe/{recipeId}/image")
	public String showUploadForm(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile multipartFile) {
		this.imageService.saveImageFile(Long.valueOf(recipeId), multipartFile);
		return "redirect:/recipe/" + recipeId + "/show";
	}
	
	@GetMapping("/recipe/{recipeId}/recipeimage")
	public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
		RecipeCommand findRecipeCommandById = this.recipeService.findRecipeCommandById(Long.valueOf(recipeId));
		Byte[] image = findRecipeCommandById.getImage();
		
		byte[] byteArray = new byte[image.length];
		
		int i= 0;
		for(Byte b : image) {
			byteArray[i++] = b;
		}
		
		
		response.setContentType("image/jpeg");
		
		InputStream is = new ByteArrayInputStream(byteArray);
		
		IOUtils.copy(is, response.getOutputStream());
	}
}
