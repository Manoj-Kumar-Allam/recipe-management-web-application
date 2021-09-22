package com.ma.recipeapp.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notes {
	
	private String id= UUID.randomUUID().toString();

	private String recipeNotes;

}
