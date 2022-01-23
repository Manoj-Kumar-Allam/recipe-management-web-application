package com.ma.recipeapp.service.impl;

import org.springframework.stereotype.Service;

import com.ma.recipeapp.commands.UnitOfMeasureCommand;
import com.ma.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.ma.recipeapp.repository.reactive.UnitOfMeasureReactiveRepository;
import com.ma.recipeapp.service.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
	
	private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	
	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		super();
		this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}

	@Override
	public Flux<UnitOfMeasureCommand> listAllUnitOfMeasures() {
		log.debug("Fetching all the Measures..");
		return unitOfMeasureReactiveRepository.findAll().map(unitOfMeasureToUnitOfMeasureCommand::convert);
		
//		return StreamSupport.stream(this.unitOfMeasureRepository.findAll().spliterator(), false)
//				.map(this.unitOfMeasureToUnitOfMeasureCommand::convert).collect(Collectors.toSet());
	}

}

