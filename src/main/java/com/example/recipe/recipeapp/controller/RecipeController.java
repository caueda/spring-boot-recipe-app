package com.example.recipe.recipeapp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.recipe.recipeapp.domain.Recipe;
import com.example.recipe.recipeapp.dto.RecipeDTO;
import com.example.recipe.recipeapp.service.RecipeService;
import com.example.recipe.recipeapp.v1.mapper.RecipeMapper;

import io.swagger.annotations.ApiOperation;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = {"*"}, exposedHeaders = {"Location"})
@RolesAllowed("user")
@RequestMapping("/api/v1/recipes")
public class RecipeController {
	private RecipeService recipeService;
	private RecipeMapper recipeMapper;
	
	public RecipeController(RecipeService recipeService, RecipeMapper recipeMapper) {
		this.recipeService = recipeService;
		this.recipeMapper = recipeMapper;
	}
	
	@ApiOperation(value = "This will get a list of recipes")
	@GetMapping
	public ResponseEntity<List<RecipeDTO>> findAll() {
		List<Recipe> result = recipeService.findAll();
		List<RecipeDTO> resultDTO = 
			result.stream().map(recipeMapper::toDTO)
				.collect(Collectors.toList());
		return new ResponseEntity<List<RecipeDTO>> (resultDTO, HttpStatus.OK);
	}
	
	@ApiOperation(value = "This will get a specific recipe by its identification id")
	@GetMapping("/{id}")
	public ResponseEntity<RecipeDTO> findById(@PathVariable String id) {
		RecipeDTO dto = recipeService.findById(id)
				.map(recipeMapper::toDTO)
				.map(recipeDTO -> {
					return recipeDTO;
				})
				.get();
		Link withSelfRel = linkTo(methodOn(this.getClass()).findAll()).withRel("all-recipes");
		dto.add(withSelfRel);
		return new ResponseEntity<RecipeDTO>(dto, HttpStatus.OK);
	}
	
	@PostMapping
    public ResponseEntity<Object> createNewRecipe(@RequestBody RecipeDTO recipeDTO){
		Recipe recipe = recipeMapper.toEntity(recipeDTO);
		Recipe recipeCreated = recipeService.insert(recipe);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(recipeCreated.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
	
	@PutMapping("/all")
    public ResponseEntity<Object> putAllRecipes(@RequestBody RecipeDTO[] recipesDTO){
        try {
        	List<Recipe> recipesList = Arrays.asList(recipesDTO)
				        	.stream()
				        	.map(recipeMapper::toEntity)
				        	.collect(Collectors.toList());
        	Recipe[] recipes = new Recipe[recipesList.size()];
        	recipes = recipesList.toArray(recipes);
        	recipeService.saveAll(recipes);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().build();
    }
	
	@PutMapping()
    public ResponseEntity<Object> putRecipes(@RequestBody RecipeDTO recipeDTO){
        try {
        	Recipe recipe = recipeMapper.toEntity(recipeDTO);
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
