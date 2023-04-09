package com.springboot.blog.services.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.springboot.blog.entities.User;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.mappers.UserMapper;
import com.springboot.blog.payloads.UserDto;
import com.springboot.blog.repositories.UserRepository;
import com.springboot.blog.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;

	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	// CREATE:

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.userRepo.save(UserMapper.mapToUser(userDto));
		return UserMapper.mapToUserDto(user);
	}

	// UPDATE:
	
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		// User savedUser = null;

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId.toString()));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		/*
		 * if(user != null) { return
		 * UserMapper.mapToUserDto(this.userRepo.save(UserMapper.mapToUser(userDto))); }
		*/
		return UserMapper.mapToUserDto(this.userRepo.save(user));
		
	}

	// PARTIAL UPDATE:
	
	@Override
	@Transactional
	public UserDto partialUpdateUser(Map<String, Object> fields, Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

		if(user != null) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(User.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, user, value);
			});
		}
		return UserMapper.mapToUserDto(this.userRepo.save(user));
	}

	// GET ONE:
	
	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId.toString()));
		return UserMapper.mapToUserDto(user);
	}

	// GET ALL
	
	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
	}

	// DELETE:
	
	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId.toString()));
		this.userRepo.delete(user);
	}

}
