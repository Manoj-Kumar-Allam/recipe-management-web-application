package com.ma.recipeapp.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingredient {
	
	private String id = UUID.randomUUID().toString();

	private String description;

	private BigDecimal amount;

	private UnitOfMeasure unitOfMeasure;

	public Ingredient() {
		super();
	}

	public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
		super();
		this.description = description;
		this.amount = amount;
		this.unitOfMeasure = unitOfMeasure;
	}

}
