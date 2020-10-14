package com.example.recipe.recipeapp.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.recipe.recipeapp.controller.exceptions.CustomizedResponseEntityExceptionHandler;
import com.example.recipe.recipeapp.domain.Recipe;
import com.example.recipe.recipeapp.service.RecipeService;

class RecipeControllerTest extends AbstractRestControllerTest {
	private static final String API_V1_RECIPES = "/api/v1/recipes";

	private static final String RECIPE_ID = "5f6761125f55f6327447df3c";

	@Mock
	RecipeService recipeService;
	
	@InjectMocks
	RecipeController recipeController;
	
	MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(recipeController)
				.setControllerAdvice(new CustomizedResponseEntityExceptionHandler())
				.build();
	}

	@Test
	void testFindAll() throws Exception {
		Recipe recipe = Recipe.builder().build();
		List<Recipe> resultList = new ArrayList<>();
		resultList.add(recipe);
		
		Mockito.when(recipeService.findAll()).thenReturn(resultList);
		
		mockMvc.perform(get(API_V1_RECIPES)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	void testFindById() throws Exception {
		Recipe recipe = Recipe.builder()
				.id(RECIPE_ID)
				.name("Burguer")
				.description("Best burguer ever")
				.build();
		Mockito.when(recipeService.findById(Mockito.anyString())).thenReturn(Optional.of(recipe));
		mockMvc.perform(get(API_V1_RECIPES + "/{id}", RECIPE_ID)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", equalTo(RECIPE_ID)));
	}
	
	@Test
	void testFindById_WhenRecipeNotFound() throws Exception {
		Mockito.when(recipeService.findById(Mockito.anyString())).thenThrow(new NoSuchElementException("No value present"));
		mockMvc.perform(get(API_V1_RECIPES + "/{id}", RECIPE_ID)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message", equalTo("No value present")));
	}

	@Test
	void testCustomizedResponseEntityExceptionHandler() throws Exception {
		String failureMessage = "Mocking the failure";
		Mockito.when(recipeService.findById(Mockito.anyString())).thenThrow(new IllegalArgumentException(failureMessage));
		mockMvc.perform(get(API_V1_RECIPES + "/{id}", RECIPE_ID)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isInternalServerError())
			.andExpect(jsonPath("$.message", equalTo(failureMessage)));
	}
	
	@Test
	void testCreateNewRecipe() throws Exception {
		Recipe recipe = Recipe.builder()
				.name("Burguer")
				.description("Best burguer ever")
				.build();
		Mockito.when(recipeService.insert(Mockito.any(Recipe.class)))
			.thenReturn(Recipe.builder().id(RECIPE_ID).build());
		mockMvc.perform(post(API_V1_RECIPES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(recipe)))
			.andExpect(status().isCreated())
			//.andExpect(header().string("Location", "")
			.andExpect(redirectedUrlPattern("http://*/*/*/recipes/" + RECIPE_ID));
	}
	
	@Test
	void testSaveRecipe() throws Exception {
		Recipe recipe = Recipe.builder()
				.name("Burguer")
				.description("Best burguer ever")
				.build();
		mockMvc.perform(put(API_V1_RECIPES + "/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Recipe[] {recipe})))
			.andExpect(status().isOk());
	}
	
	@Test
	void testSaveRecipe_WhenInternalError_happens() throws Exception {
		Recipe recipe = Recipe.builder()
				.name("Burguer")
				.description("Best burguer ever")
				.build();
		Mockito.when(recipeService.saveAll(Mockito.any(Recipe[].class)))
			.thenThrow(new IllegalArgumentException("bad request"));
		mockMvc.perform(put(API_V1_RECIPES + "/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(recipe)))
			.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
	
	@Test
	void testDeleteRecipe() throws Exception {
		Mockito.when(recipeService.saveAll(Mockito.any(Recipe[].class)))
			.thenThrow(new IllegalArgumentException("bad request"));
		mockMvc.perform(delete(API_V1_RECIPES + "/{id}", RECIPE_ID))
			.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteRecipe_WhenInternalError_happens() throws Exception {
		Mockito.doThrow(new IllegalArgumentException("delete error")).when(recipeService).delete(Mockito.anyString());
		mockMvc.perform(delete(API_V1_RECIPES + "/{id}", RECIPE_ID))
			.andExpect(jsonPath("$", equalTo("delete error")))
			.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
}
