package com.springboot.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private int id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String about;
}
