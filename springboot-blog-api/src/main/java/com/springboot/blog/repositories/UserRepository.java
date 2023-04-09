package com.springboot.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
}
