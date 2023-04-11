package com.springboot.blog.services;

import java.util.Map;

import com.springboot.blog.payloads.CategoryDto;
import com.springboot.blog.payloads.GetAllResponse;


public interface CategoryService {
	
	// CREATE:
	CategoryDto createCategory(CategoryDto categoryDto);
	
	// UPDATE:
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	// PATCH:
	CategoryDto patchCategory(Map<String, Object> fields, Integer categoryId);
	
	// GET ALL:
	GetAllResponse getCategories(Integer pageNumber, Integer pageSize);
	
	// GET ONE:
	CategoryDto getCategory(Integer categoryId);
	
	// DELETE:
	void deleteCategory(Integer categoryId);
	
}
