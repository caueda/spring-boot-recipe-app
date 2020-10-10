package com.example.recipe.recipeapp.service;

import java.util.List;
import java.util.Optional;

import com.example.recipe.recipeapp.domain.Recipe;

public interface RecipeService {

	List<Recipe> findAll();
	
	Optional<Recipe> findById(String id);
	
	Recipe insert(Recipe recipe);
	
	Recipe save(Recipe recipe);
	
	void delete(String id);
}