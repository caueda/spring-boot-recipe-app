package com.example.recipe.recipeapp.v1.mapper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.recipe.recipeapp.domain.Ingredient;
import com.example.recipe.recipeapp.domain.Recipe;
import com.example.recipe.recipeapp.dto.IngredientDTO;
import com.example.recipe.recipeapp.dto.RecipeDTO;

class RecipeMapperTest {
	RecipeMapper mapper = RecipeMapper.INSTANCE;

	@Test
	void testToDTO() {
		Recipe entity = new Recipe();
		entity.setId("23dsfadf");
		entity.setName("Bread");
		entity.setName("Flour");
		entity.setDescription("test");
		entity.setImagePath("http://test.com/image.jpg");
		List<Ingredient> ingredients = new ArrayList<>();
		Ingredient ingredient = new Ingredient();
		ingredient.setAmount(1);
		ingredients.add(ingredient);
		entity.setIngredients(ingredients);
		
		RecipeDTO dto = mapper.toDTO(entity);
		assertThat(dto.getId(), equalTo(entity.getId()));
		assertThat(dto.getName(), equalTo(entity.getName()));
		assertThat(dto.getDescription(), equalTo(entity.getDescription()));
		assertThat(dto.getImagePath(), equalTo(entity.getImagePath()));
		assertThat(dto.getIngredients(), hasSize(1));
		assertThat(dto.getIngredients().get(0).getName(), equalTo(entity.getIngredients().get(0).getName()));
	}

	@Test
	void testToEntity() {
		RecipeDTO dto = new RecipeDTO();
		dto.setId("23dsfadf");
		dto.setName("Bread");
		dto.setName("Flour");
		dto.setDescription("test");
		dto.setImagePath("http://test.com/image.jpg");
		List<IngredientDTO> ingredients = new ArrayList<>();
		IngredientDTO ingredientDTO = new IngredientDTO();
		ingredientDTO.setAmount(1);
		ingredients.add(ingredientDTO);
		dto.setIngredients(ingredients);
		
		Recipe entity = mapper.toEntity(dto);
		assertThat(entity.getId(), equalTo(dto.getId()));
		assertThat(entity.getName(), equalTo(dto.getName()));
		assertThat(entity.getDescription(), equalTo(dto.getDescription()));
		assertThat(entity.getImagePath(), equalTo(dto.getImagePath()));
		assertThat(entity.getIngredients(), hasSize(1));
		assertThat(entity.getIngredients().get(0).getName(), equalTo(dto.getIngredients().get(0).getName()));
	}
}
