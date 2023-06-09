package com.springboot.blog.payloads;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	
	@NotBlank
	@Size(max = 28, message = "Post title should not be empty or should not be greater than 28 characters!")
	private String title;
	
	@NotBlank(message = "Post content should not be empty")
	private String content;
	
	private String imageName;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comments = new HashSet<>();
	
}
