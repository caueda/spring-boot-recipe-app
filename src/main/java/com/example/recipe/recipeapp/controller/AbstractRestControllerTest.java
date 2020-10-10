package com.example.recipe.recipeapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractRestControllerTest {
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
