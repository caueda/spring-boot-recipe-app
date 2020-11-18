package com.example.recipe.recipeapp.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.recipe.recipeapp.domain.Recipe;
import com.example.recipe.recipeapp.dto.RecipeDTO;

@Mapper
public interface RecipeMapper {
	RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);
	RecipeDTO toDTO(Recipe recipe);
	Recipe toEntity(RecipeDTO recipeDTO);
}
