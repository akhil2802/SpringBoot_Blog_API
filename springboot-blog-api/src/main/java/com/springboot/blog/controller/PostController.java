package com.springboot.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.springboot.blog.config.AppConstants;
import com.springboot.blog.payloads.ApiResponse;
import com.springboot.blog.payloads.GetAllResponse;
import com.springboot.blog.payloads.PostDto;
import com.springboot.blog.services.FileService;
import com.springboot.blog.services.PostService;

@RestController
@RequestMapping("/api/v1/")
public class PostController {

	private final PostService postService;
	private final FileService fileService;
	
	@Value("${project.image}")
	private String path;

	public PostController(PostService postService, FileService fileService) {
		this.postService = postService;
		this.fileService = fileService;
	}

	// CREATE:

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		return new ResponseEntity<PostDto>(this.postService.createPost(postDto, userId, categoryId),
				HttpStatus.CREATED);
	}

	// UPDATE:

	@PutMapping("/posts/{postId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
		return ResponseEntity.ok(this.postService.updatePost(postDto, postId));
	}

	// PATCH:

	@PatchMapping("/posts/{postId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> patchPost(@Valid @RequestBody Map<String, Object> fields,
			@PathVariable Integer postId) {
		return ResponseEntity.ok(this.postService.patchPost(fields, postId));
	}

	// GET ALL:

	@GetMapping("/posts")
	public ResponseEntity<GetAllResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.POST_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {
		return ResponseEntity.ok(this.postService.getPosts(pageNumber, pageSize, sortBy, sortOrder));
	}

	// GET ONE:

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer postId) {
		return ResponseEntity.ok(this.postService.getPostById(postId));
	}

	// DELETE:

	@DeleteMapping("/posts/{postId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		return new ResponseEntity<ApiResponse>(
				new ApiResponse("Post with Post Id" + postId + "has been deleted successfully!", true), HttpStatus.OK);
	}

	// GET ALL BY USER:

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
		return new ResponseEntity<List<PostDto>>(this.postService.getPostsByUser(userId), HttpStatus.OK);
	}

	// GET BY CATEGORY:

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		return new ResponseEntity<List<PostDto>>(this.postService.getPostsByCategory(categoryId), HttpStatus.OK);
	}

	// SEARCH:

	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword) {
		return ResponseEntity.ok(this.postService.searchPosts(keyword));
	}

	// Post Image Upload:

	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage (
			@RequestParam(value = "image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {
		
		PostDto postDto = this.postService.getPostById(postId);
		
		String imageName = this.fileService.uploadImage(path, image);
		postDto.setImageName(imageName);		
		
		return ResponseEntity.ok(this.postService.updatePost(postDto, postId));
	}
	
	// Post Image Download:
	
	@GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadPostImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
