package com.example.recipe.recipeapp.domain;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Ingredient extends RepresentationModel<Ingredient>{
	private String name;
	private Integer amount;
	
	@Builder
	public Ingredient(String name, Integer amount) {
		super();
		this.name = name;
		this.amount = amount;
	}
}
