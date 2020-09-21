package com.example.recipe.recipeapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.recipe.recipeapp.domain.Recipe;

public interface RecipeRepository extends MongoRepository<Recipe, String>{

}
