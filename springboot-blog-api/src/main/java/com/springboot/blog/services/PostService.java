package com.springboot.blog.services;

import java.util.List;
import java.util.Map;

import com.springboot.blog.payloads.PostDto;

public interface PostService {
	
	// CREATE:
	PostDto createPost(PostDto postDto, Integer userId, Integer CategoryId);
	
	// UPDATE:
	PostDto updatePost(PostDto postDto, Integer postId);
	
	// PATCH:
	PostDto patchPost(Map<String, Object> fields, Integer postId);
	
	// GET ALL:
	List<PostDto> getPosts(Integer pageNumber, Integer pageSize);
	
	// GET ONE:
	PostDto getPostById(Integer postId);
	
	// DELETE:
	void deletePost(Integer postId);
	
	// GET ALL BY CATEGORY:
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	// GET ALL BY USER:
	List<PostDto> getPostsByUser(Integer userId);
	
	// SEARCH ALL BY KEYWORD:
	List<PostDto> searchPosts(String keyword); 
}
