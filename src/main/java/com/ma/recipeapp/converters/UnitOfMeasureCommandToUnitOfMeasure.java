package com.ma.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.ma.recipeapp.commands.UnitOfMeasureCommand;
import com.ma.recipeapp.model.UnitOfMeasure;

import lombok.Synchronized;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure>{

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null) {
            return null;
        }

        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(source.getId());
        uom.setDescription(source.getDescription());
        return uom;
    }
}
