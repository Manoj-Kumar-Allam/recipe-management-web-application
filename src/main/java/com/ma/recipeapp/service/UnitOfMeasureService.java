package com.ma.recipeapp.service;

import com.ma.recipeapp.commands.UnitOfMeasureCommand;

import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {
	
	Flux<UnitOfMeasureCommand> listAllUnitOfMeasures();
}
