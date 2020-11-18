package com.example.recipe.recipeapp.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.recipe.recipeapp.domain.Ingredient;
import com.example.recipe.recipeapp.dto.IngredientDTO;

@Mapper
public interface IngredientMapper {
	IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);
	IngredientDTO toDTO(Ingredient ingredient);
	Ingredient toEntity(IngredientDTO ingredientDTO);
}
