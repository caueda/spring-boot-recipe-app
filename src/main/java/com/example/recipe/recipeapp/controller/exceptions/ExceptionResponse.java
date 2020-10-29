package com.example.recipe.recipeapp.controller.exceptions;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ExceptionResponse {
	private Integer status;
	private String error;
	private String message;
	private Date timestamp;
	private String detail;
	
	@Builder
	public ExceptionResponse(Integer status, String error, String message, Date timestamp, String detail) {
		super();
		this.status = status;
		this.error = error;
		this.message = message;
		this.timestamp = timestamp;
		this.detail = detail;
	}
}
