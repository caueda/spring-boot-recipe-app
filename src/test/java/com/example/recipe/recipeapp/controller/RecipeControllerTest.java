package com.example.recipe.recipeapp.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.recipe.recipeapp.domain.Recipe;
import com.example.recipe.recipeapp.service.RecipeService;

class RecipeControllerTest {
	@Mock
	RecipeService recipeService;
	
	@InjectMocks
	RecipeController recipeController;
	
	MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
	}

	@Test
	void testFindAll() throws Exception {
		Recipe recipe = Recipe.builder().build();
		List<Recipe> resultList = new ArrayList<>();
		resultList.add(recipe);
		
		Mockito.when(recipeService.findAll()).thenReturn(resultList);
		
		mockMvc.perform(get("/api/v1/recipe")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	void testFindById() throws Exception {
		Recipe recipe = Recipe.builder()
				.id("5f6761125f55f6327447df3c")
				.name("Burguer")
				.description("Best burguer ever")
				.build();
		Mockito.when(recipeService.findById(Mockito.anyString())).thenReturn(Optional.of(recipe));
		mockMvc.perform(get("/api/v1/recipe/{id}", "5f6761125f55f6327447df3c")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", equalTo("5f6761125f55f6327447df3c")));
	}

	@Test
	void testCustomizedResponseEntityExceptionHandler() throws Exception {
		String failureMessage = "Mocking the failure";
		Mockito.when(recipeService.findById(Mockito.anyString())).thenThrow(new IllegalArgumentException(failureMessage));
		mockMvc.perform(get("/api/v1/recipe/{id}", "5f6761125f55f6327447df3c")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isInternalServerError())
			.andExpect(jsonPath("$.message", equalTo(failureMessage)));
	}
}
