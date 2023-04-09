package com.springboot.blog.services;

import java.util.List;
import java.util.Map;

import com.springboot.blog.payloads.CategoryDto;


public interface CategoryService {
	
	// CREATE:
	CategoryDto createCategory(CategoryDto categoryDto);
	
	// UPDATE:
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	// PATCH:
	CategoryDto patchCategory(Map<String, Object> fields, Integer categoryId);
	
	// GET ALL:
	List<CategoryDto> getCategories();
	
	// GET ONE:
	CategoryDto getCategory(Integer categoryId);
	
	// DELETE:
	void deleteCategory(Integer categoryId);
	
}
