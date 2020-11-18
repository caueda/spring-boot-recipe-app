package com.example.recipe.recipeapp.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class IngredientDTO extends RepresentationModel<IngredientDTO>{
	private String name;
	private Integer amount;
	
	@Builder
	public IngredientDTO(String name, Integer amount) {
		super();
		this.name = name;
		this.amount = amount;
	}
}
