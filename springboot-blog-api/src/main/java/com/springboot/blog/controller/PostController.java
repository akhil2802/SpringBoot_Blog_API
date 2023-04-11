package com.springboot.blog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.springboot.blog.payloads.ApiResponse;
import com.springboot.blog.payloads.GetAllResponse;
import com.springboot.blog.payloads.PostDto;
import com.springboot.blog.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	// CREATE:

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		return new ResponseEntity<PostDto>(this.postService.createPost(postDto, userId, categoryId),
				HttpStatus.CREATED);
	}

	// UPDATE:

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
		return ResponseEntity.ok(this.postService.updatePost(postDto, postId));
	}

	// PATCH:

	@PatchMapping("/posts/{postId}")
	public ResponseEntity<PostDto> patchPost(@Valid @RequestBody Map<String, Object> fields,
			@PathVariable Integer postId) {
		return ResponseEntity.ok(this.postService.patchPost(fields, postId));
	}

	// GET ALL:

	@GetMapping("/posts")
	public ResponseEntity<GetAllResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
		return ResponseEntity.ok(this.postService.getPosts(pageNumber, pageSize));
	}

	// GET ONE:

	@GetMapping("posts/{postId}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer postId) {
		return ResponseEntity.ok(this.postService.getPostById(postId));
	}

	// DELETE:

	@DeleteMapping("/posts/{postId}")
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

}
