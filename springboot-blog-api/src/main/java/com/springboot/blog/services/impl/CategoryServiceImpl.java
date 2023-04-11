package com.springboot.blog.services.impl;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
<<<<<<< HEAD
=======
import org.springframework.data.domain.Pageable;
>>>>>>> adcbac23a58f15769937523596ee272cd6ba6119
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.springboot.blog.entities.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payloads.CategoryDto;
import com.springboot.blog.payloads.GetAllResponse;
import com.springboot.blog.repositories.CategoryRepository;
import com.springboot.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepository categoryRepo;

	// CREATE:

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	// UPDATE:

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId.toString()));

		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());

		Category savedCategory = this.categoryRepo.save(category);

		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	// PATCH:

	@Override
	public CategoryDto patchCategory(Map<String, Object> fields, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId.toString()));

		if (category != null) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(Category.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, category, value);
			});
		}

		return this.modelMapper.map(this.categoryRepo.save(category), CategoryDto.class);
	}

	// GET ALL:

	@Override
	public GetAllResponse getCategories(Integer pageNumber, Integer pageSize) {

<<<<<<< HEAD
		Page<Category> pageCategory = this.categoryRepo.findAll(PageRequest.of(pageNumber, pageSize));

		return new GetAllResponse(
				pageCategory.getContent().stream().map((category) -> this.modelMapper.map(category, CategoryDto.class))
						.collect(Collectors.toList()),
				pageCategory.getNumber(), pageCategory.getSize(), pageCategory.getNumberOfElements(),
				pageCategory.getTotalElements(), pageCategory.getTotalPages(), pageCategory.isLast());
=======
		Pageable p = PageRequest.of(pageNumber, pageSize);
		Page<Category> pageCategory = this.categoryRepo.findAll(p);
		List<Category> categories = pageCategory.getContent();
		List<CategoryDto> categoryDtos = categories.stream()
				.map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

		return new GetAllResponse(categoryDtos, pageCategory.getNumber(), pageCategory.getSize(),
				pageCategory.getNumberOfElements(), pageCategory.getTotalElements(), pageCategory.getTotalPages(),
				pageCategory.isLast());
>>>>>>> adcbac23a58f15769937523596ee272cd6ba6119
	}

	// GET ONE:

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId.toString()));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	// DELETE:

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId.toString()));
		this.categoryRepo.delete(category);
	}

}
