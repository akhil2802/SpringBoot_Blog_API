package com.springboot.blog.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.springboot.blog.config.AppConstants;
import com.springboot.blog.payloads.ApiResponse;
import com.springboot.blog.payloads.GetAllResponse;
import com.springboot.blog.payloads.UserDto;
import com.springboot.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// POST:

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createdUserDto, HttpStatus.CREATED);
	}

	// PUT:

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		UserDto updatedUserDto = this.userService.updateUser(userDto, userId);
		// return new ResponseEntity<UserDto>(updatedUserDto, HttpStatus.OK);
		return ResponseEntity.ok(updatedUserDto);
	}

	// PATCH:

	@PatchMapping("/{userId}")
	public ResponseEntity<UserDto> patchUser(@Valid @RequestBody Map<String, Object> fields,
			@PathVariable Integer userId) {
		return ResponseEntity.ok(this.userService.partialUpdateUser(fields, userId));
	}

	// DELETE:
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);
		// return new ResponseEntity(Map.of("message", "User Deleted Successfully!"),
		// HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(
				new ApiResponse("User with id " + userId + " has been deleted Successfully!", true), HttpStatus.OK);
	}

	// GET ALL:
	
	@GetMapping
	public ResponseEntity<GetAllResponse> getUsers(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.USER_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {
		return ResponseEntity.ok(this.userService.getAllUsers(pageNumber, pageSize, sortBy, sortOrder));
	}

	// GET ONE:

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}
