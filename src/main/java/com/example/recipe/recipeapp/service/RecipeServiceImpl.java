package com.example.recipe.recipeapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.recipe.recipeapp.domain.Recipe;
import com.example.recipe.recipeapp.repository.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {

	private RecipeRepository recipeRepository;
	
	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}
	
	@Override
	public List<Recipe> findAll() {
		return this.recipeRepository.findAll();
	}

	@Override
	public Optional<Recipe> findById(String id) {
		
		return recipeRepository.findById(id);
	}
}
