package com.springboot.blog.services;

import java.util.Map;

import com.springboot.blog.payloads.GetAllResponse;
import com.springboot.blog.payloads.UserDto;

public interface UserService {

	// CREATE:
	UserDto createUser(UserDto userDto);

	// UPDATE:
	UserDto updateUser(UserDto userDto, Integer userId);

	// PATCH:
	UserDto partialUpdateUser(Map<String, Object> fields, Integer userId);

	// GET ONE:
	UserDto getUserById(Integer userId);

	// GET ALL:
	GetAllResponse getAllUsers(Integer pageNumber, Integer pageSize);

	// DELETE:
	void deleteUser(Integer userId);

}
