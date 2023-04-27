//package com.springboot.blog.mappers;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.springboot.blog.entities.User;
//import com.springboot.blog.payloads.UserDto;
//
//public class UserMapper {
//	
//	@Autowired
//	private static ModelMapper modelMapper;
//	
//	public static UserDto mapToUserDto(User user) {
//
////		return UserDto.builder().id(user.getId()).name(user.getName()).about(user.getAbout()).email(user.getEmail())
////				.password(user.getPassword()).build();
//		return this.odelMapper.map(user, UserDto.class);
//	}
//
//	public static User mapToUser(UserDto userDto) {
//
//		return User.builder().id(userDto.getId()).name(userDto.getName()).about(userDto.getAbout()).email(userDto.getEmail())
//				.password(userDto.getPassword()).build();
////		return modelMapper.map(userDto, User.class);
//	}
//}
