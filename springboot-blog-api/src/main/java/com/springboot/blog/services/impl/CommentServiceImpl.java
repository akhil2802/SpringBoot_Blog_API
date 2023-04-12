package com.springboot.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.blog.entities.Comment;
import com.springboot.blog.entities.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payloads.CommentDto;
import com.springboot.blog.repositories.CommentRepository;
import com.springboot.blog.repositories.PostRepository;
import com.springboot.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	private final PostRepository postRepo;
	private final CommentRepository commentRepo;
	private final ModelMapper modelMapper;
	
	public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo, ModelMapper modelMapper) {
		this.postRepo = postRepo;
		this.commentRepo = commentRepo;
		this.modelMapper = modelMapper;
	}

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId.toString()));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		
		return this.modelMapper.map(this.commentRepo.save(comment), CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId.toString()));
		this.commentRepo.delete(comment);
	}

}
