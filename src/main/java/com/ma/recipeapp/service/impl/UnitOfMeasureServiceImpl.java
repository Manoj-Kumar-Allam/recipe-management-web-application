package com.ma.recipeapp.service.impl;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.ma.recipeapp.commands.UnitOfMeasureCommand;
import com.ma.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.ma.recipeapp.repository.UnitOfMeasureRepository;
import com.ma.recipeapp.service.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
	
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	
	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		super();
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}

	@Override
	public Set<UnitOfMeasureCommand> listAllUnitOfMeasures() {
		log.debug("Fetching all the Measures..");
		return StreamSupport.stream(this.unitOfMeasureRepository.findAll().spliterator(), false).map(this.unitOfMeasureToUnitOfMeasureCommand::convert).collect(Collectors.toSet());
	}

}

