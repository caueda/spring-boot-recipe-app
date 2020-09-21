package com.example.recipe.recipeapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "recipe")
@NoArgsConstructor
@Getter @Setter
public class Recipe {
	@Id
	private String id;
	private String name;
	private String description;
	
	@Builder
	public Recipe(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
}
