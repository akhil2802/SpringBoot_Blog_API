package com.springboot.blog.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payloads.ApiResponse;
import com.springboot.blog.payloads.CategoryDto;
import com.springboot.blog.payloads.GetAllResponse;
import com.springboot.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// CREATE:
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategoryDto, HttpStatus.CREATED);
	}

	// UPDATE:
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer categoryId) {
		CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updatedCategoryDto, HttpStatus.OK);
	}

	// PATCH:
	@PatchMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> patchCategory(@Valid @RequestBody Map<String, Object> fields,
			@PathVariable Integer categoryId) {
		CategoryDto patchedCategoryDto = this.categoryService.patchCategory(fields, categoryId);
		return new ResponseEntity<CategoryDto>(patchedCategoryDto, HttpStatus.OK);
	}

	// GET ALL:
	@GetMapping("/")
	public ResponseEntity<GetAllResponse> getCategories(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
			@RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder) {
		return ResponseEntity.ok(this.categoryService.getCategories(pageNumber, pageSize, sortBy, sortOrder));
	}

	// GET ONE:
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(this.categoryService.getCategory(categoryId));
	}

	// DELETE:
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(
				new ApiResponse("Category with Category Id " + categoryId + "has been deleted Successfully!", true),
				HttpStatus.OK);
	}

}
