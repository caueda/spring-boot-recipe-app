package com.example.recipe.recipeapp.v1.mapper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.example.recipe.recipeapp.domain.Ingredient;
import com.example.recipe.recipeapp.dto.IngredientDTO;

class IngredientMapperTest {
	IngredientMapper mapper = IngredientMapper.INSTANCE;

	@Test
	void testToDTO() {
		Ingredient entity = new Ingredient();
		entity.setName("Carrot");
		entity.setAmount(10);
		IngredientDTO dto = mapper.toDTO(entity);
		assertThat(dto.getName(), equalTo(entity.getName()));
		assertThat(dto.getAmount(), equalTo(entity.getAmount()));
	}

	@Test
	void testToEntity() {
		IngredientDTO dto = new IngredientDTO();
		dto.setName("Onion");
		dto.setAmount(1);
		Ingredient entity = mapper.toEntity(dto);
		assertThat(entity.getName(), equalTo(dto.getName()));
		assertThat(entity.getAmount(), equalTo(dto.getAmount()));
	}

}
