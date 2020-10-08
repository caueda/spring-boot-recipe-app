package com.example.recipe.recipeapp.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.recipe.recipeapp.domain.Recipe;
import com.example.recipe.recipeapp.repository.RecipeRepository;

class RecipeServiceImplTest {
	
	@Mock
	RecipeRepository recipeRepository;
	
	RecipeService recipeService;
	
	Recipe mockRecipe = null;
	
	List<Recipe> listRecipe = new ArrayList<>();
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository);
		mockRecipe = Recipe.builder().build();
		listRecipe.add(mockRecipe);
	}
	
	@Test
	public void testFindAll() {
		Mockito.when(recipeRepository.findAll()).thenReturn(listRecipe);
		assertThat(1, equalTo(recipeService.findAll().size()));
	}

	@Test
	public void testFindById() {
		Mockito.when(recipeRepository.findById(Mockito.anyString())).thenReturn(Optional.of(mockRecipe));
		assertThat(true, is(recipeService.findById("").isPresent()));
	}

}
