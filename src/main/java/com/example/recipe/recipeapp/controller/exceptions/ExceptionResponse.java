package com.example.recipe.recipeapp.controller.exceptions;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ExceptionResponse {
	private String message;
	private Date timestamp;
	private String detail;
	
	@Builder
	public ExceptionResponse(String message, Date timestamp, String detail) {
		super();
		this.message = message;
		this.timestamp = timestamp;
		this.detail = detail;
	}
}
