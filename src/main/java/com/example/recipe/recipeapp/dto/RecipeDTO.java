package com.example.recipe.recipeapp.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RecipeDTO extends RepresentationModel<RecipeDTO>{
	@ApiModelProperty(value = "The identification of the recipe")
	@Id
	private String id;
	@ApiModelProperty(value = "The name of the recipe")
	private String name;
	@ApiModelProperty(value = "A short description of the recipe")
	private String description;
	@ApiModelProperty(value = "The url for the recipe image")
	private String imagePath;
	@ApiModelProperty(value = "Ingredients list")
	private List<IngredientDTO> ingredients = new ArrayList<>();
	
	@Builder
	public RecipeDTO(String id, String name, String description, String imagePath, List<IngredientDTO> ingredients) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.imagePath = imagePath;
		this.ingredients = (ingredients == null) ? new ArrayList<>() : ingredients;
	}
}
