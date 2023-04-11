package com.springboot.blog.services.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.springboot.blog.entities.Category;
import com.springboot.blog.entities.Post;
import com.springboot.blog.entities.User;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payloads.GetAllResponse;
import com.springboot.blog.payloads.PostDto;
import com.springboot.blog.repositories.CategoryRepository;
import com.springboot.blog.repositories.PostRepository;
import com.springboot.blog.repositories.UserRepository;
import com.springboot.blog.services.PostService;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepo;
	private final ModelMapper modelMapper;
	private final UserRepository userRepo;
	private final CategoryRepository categoryRepo;

	public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper, UserRepository userRepo,
			CategoryRepository categoryRepo) {
		this.postRepo = postRepo;
		this.modelMapper = modelMapper;
		this.userRepo = userRepo;
		this.categoryRepo = categoryRepo;
	}

	// CREATE:

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", categoryId.toString()));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId.toString()));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setUser(user);
		post.setCategory(category);

		return this.modelMapper.map(this.postRepo.save(post), PostDto.class);
	}

	// UPDATE:

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId.toString()));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());

		return this.modelMapper.map(this.postRepo.save(post), PostDto.class);
	}

	// PATCH:

	@Override
	@Transactional
	public PostDto patchPost(Map<String, Object> fields, Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId.toString()));

		if (post != null) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(Post.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, postId, value);
			});
		}

		return this.modelMapper.map(this.postRepo.save(post), PostDto.class);
	}

	// GET ALL:

	@Override
	public GetAllResponse getPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		
		Sort sort = (sortOrder.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Page<Post> pagePost = this.postRepo.findAll(PageRequest.of(pageNumber, pageSize, sort));

		return new GetAllResponse(
				pagePost.getContent().stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(
						Collectors.toList()),
				pagePost.getNumber(), pagePost.getSize(), pagePost.getNumberOfElements(), pagePost.getTotalElements(),
				pagePost.getTotalPages(), pagePost.isLast());
	}

	// GET ONE:

	@Override
	public PostDto getPostById(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId.toString()));
		return this.modelMapper.map(post, PostDto.class);
	}

	// DELETE:

	@Override
	public void deletePost(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId.toString()));
		this.postRepo.delete(post);
	}

	// GET BY CATEGORY:

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId.toString()));

		List<Post> posts = this.postRepo.findByCategory(category);
		
		return posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	// GET BY USER:

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId.toString()));

		List<Post> posts = this.postRepo.findByUser(user);

		return posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	// SEARCH:

	@Override
	public List<PostDto> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
