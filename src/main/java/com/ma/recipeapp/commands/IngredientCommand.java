package com.ma.recipeapp.commands;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by jt on 6/21/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
	
    private String id;
    private String recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasure;

}
