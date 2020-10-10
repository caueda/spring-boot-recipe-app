package com.example.recipe.recipeapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.recipe.recipeapp.domain.Recipe;
import com.example.recipe.recipeapp.service.RecipeService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/v1/recipes")
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
	
	@PostMapping
    public ResponseEntity<Object> createNewRecipe(@RequestBody Recipe recipe){
		Recipe recipeCreated = recipeService.insert(recipe);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(recipeCreated.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
	
	@PutMapping
    public ResponseEntity<Object> updateRecipe(@RequestBody Recipe recipe){
        try {
			recipeService.save(recipe);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> updateRecipe(@PathVariable String id) {
		try {
			recipeService.delete(id);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().build();
	}
}
