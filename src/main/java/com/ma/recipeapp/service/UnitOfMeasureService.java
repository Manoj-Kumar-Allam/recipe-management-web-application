package com.ma.recipeapp.service;

import java.util.Set;

import com.ma.recipeapp.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
	
	Set<UnitOfMeasureCommand> listAllUnitOfMeasures();
}
