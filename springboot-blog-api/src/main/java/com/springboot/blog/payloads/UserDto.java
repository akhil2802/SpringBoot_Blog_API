package com.springboot.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

	@NotEmpty
	@Size(min = 3, message = "User Name must be minimum of 4 characters!")
	private String name;

	@Email(message = "Entered Email address is not valid!")
	private String email;

	@NotEmpty
 	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters and contain at least one letter and one number")
	private String password;

	@NotEmpty
	private String about;
}
