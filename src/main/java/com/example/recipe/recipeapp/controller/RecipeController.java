package com.example.recipe.recipeapp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.recipe.recipeapp.domain.Recipe;
import com.example.recipe.recipeapp.service.RecipeService;

import io.swagger.annotations.ApiOperation;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = {"*"}, exposedHeaders = {"Location"})
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
	
	@RolesAllowed("user")
	@ApiOperation(value = "This will get a specific recipe by its identification id")
	@GetMapping("/{id}")
	public ResponseEntity<Recipe> findById(@PathVariable String id, @RequestHeader String authorization) {
		Recipe recipe = recipeService.findById(id).get();
		Link withSelfRel = linkTo(methodOn(this.getClass()).findAll()).withRel("all-recipes");
		recipe.add(withSelfRel);
		return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
	}
	
	@PostMapping
    public ResponseEntity<Object> createNewRecipe(@RequestBody Recipe recipe){
		Recipe recipeCreated = recipeService.insert(recipe);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(recipeCreated.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
	
	@PutMapping("/all")
    public ResponseEntity<Object> putAllRecipes(@RequestBody Recipe[] recipes){
        try {
        	recipeService.saveAll(recipes);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().build();
    }
	
	@PutMapping()
    public ResponseEntity<Object> putRecipes(@RequestBody Recipe recipe){
        try {
        	recipeService.save(recipe);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().build();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteRecipe(@PathVariable String id) {
		recipeService.findById(id).get();
		recipeService.delete(id);
		return ResponseEntity.ok().build();
	}
}
