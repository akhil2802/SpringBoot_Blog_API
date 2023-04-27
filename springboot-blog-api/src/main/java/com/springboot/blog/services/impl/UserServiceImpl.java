package com.springboot.blog.services.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.springboot.blog.config.AppConstants;
import com.springboot.blog.entities.Role;
import com.springboot.blog.entities.User;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payloads.GetAllResponse;
import com.springboot.blog.payloads.UserDto;
import com.springboot.blog.repositories.RoleRepository;
import com.springboot.blog.repositories.UserRepository;
import com.springboot.blog.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepo;
	private final ModelMapper modelMapper;
	
	// REGISTER:
	
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		return this.modelMapper.map(this.userRepo.save(user), UserDto.class);
	}

	// CREATE:

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		User savedUser = this.userRepo.save(user);
		return this.modelMapper.map(savedUser, UserDto.class);
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
		return this.modelMapper.map(this.userRepo.save(user), UserDto.class);
		
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
		return this.modelMapper.map(this.userRepo.save(user), UserDto.class);
	}

	// GET ONE:
	
	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId.toString()));
		return this.modelMapper.map(user, UserDto.class);
	}

	// GET ALL
	
	@Override
	public GetAllResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		
		Sort sort = (sortOrder.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<User> pageUser = this.userRepo.findAll(p);
		List<User> users = pageUser.getContent();
		List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		
		GetAllResponse getResponse = new GetAllResponse();
		getResponse.setContent(userDtos);
		getResponse.setPageNumber(pageUser.getNumber());
		getResponse.setPageSize(pageUser.getSize());
		getResponse.setNumberOfElements(pageUser.getNumberOfElements());
		getResponse.setTotalNumberOfElements(pageUser.getTotalElements());
		getResponse.setTotalPages(pageUser.getTotalPages());
		getResponse.setLastPage(pageUser.isLast());
		
		return getResponse;
	}

	// DELETE:
	
	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId.toString()));
		this.userRepo.delete(user);
	}


}
