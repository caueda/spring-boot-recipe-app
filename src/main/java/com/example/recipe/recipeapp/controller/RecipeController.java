package com.example.recipe.recipeapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.recipe.recipeapp.domain.Recipe;
import com.example.recipe.recipeapp.service.RecipeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;

@Controller
@RequestMapping("/api/v1/recipe")
public class RecipeController {
	private RecipeService recipeService;
	
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@ApiOperation(value = "This will get a list of recipes")
	@GetMapping
	public ResponseEntity<List<Recipe>> findAll() {
		return new ResponseEntity<List<Recipe>> (recipeService.findAll(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "This will get a specific recipe by its identification id")
	@GetMapping("/{id}")
	public ResponseEntity<Recipe> findById(@PathVariable String id) {
		return new ResponseEntity<Recipe>(recipeService.findById(id).get(), HttpStatus.OK);
	}
}
