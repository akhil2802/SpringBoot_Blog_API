package com.springboot.blog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payloads.ApiResponse;
import com.springboot.blog.payloads.CommentDto;
import com.springboot.blog.services.CommentService;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{postId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
			@PathVariable Integer postId) {
		return new ResponseEntity<CommentDto>((this.commentService.createComment(commentDto, postId)),
				HttpStatus.CREATED);
	}

	@DeleteMapping("/{commentId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		return new ResponseEntity<ApiResponse>(
				new ApiResponse("Comment with Comment Id " + commentId + "has been deleted Successfully!", true),
				HttpStatus.OK);
	}
}
