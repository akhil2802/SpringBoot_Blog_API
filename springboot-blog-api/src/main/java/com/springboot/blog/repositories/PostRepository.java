package com.springboot.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.blog.entities.Category;
import com.springboot.blog.entities.Post;
import com.springboot.blog.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	// SEARCH:
		//	List<Post> findByTitleContaining(String keyword);
	@Query("SELECT p FROM Post p WHERE p.title LIKE :key")
	List<Post> searchByTitle(@Param("key") String title);
}
