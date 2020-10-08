package com.example.recipe.recipeapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "recipe")
@NoArgsConstructor
@Getter @Setter
public class Recipe {
	@ApiModelProperty(value = "The identification of the recipe")
	@Id
	private String id;
	@ApiModelProperty(value = "The name of the recipe")
	private String name;
	@ApiModelProperty(value = "A short description of the recipe")
	private String description;
	
	@Builder
	public Recipe(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
}
