package com.springboot.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private int id;

	@NotEmpty
	@Size(min = 3, message = "User Name must be minimum of 4 characters!")
	private String name;

	@Email(message = "Entered Email address is not valid!")
	@NotEmpty(message = "Email is required !!")
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotEmpty
 	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters and contain at least one letter and one number")
	private String password;

	@NotEmpty
	private String about;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Set<RoleDto> roles = new HashSet<>();
}
