package com.springboot.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.blog.config.AppConstants;
import com.springboot.blog.entities.Role;
import com.springboot.blog.repositories.RoleRepository;

@SpringBootApplication
public class SpringbootBlogApiApplication implements CommandLineRunner {
	
	@Autowired
	private RoleRepository roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogApiApplication.class, args);  
	}
	
	/*
	@Autowired
	private PasswordEncoder passwordEncoder;
	*/
	
	@Override
	public void run(String... args) throws Exception {
		try {
			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("ROLE_NORMAL"); 
			
			List<Role> roles = List.of(role1, role2);
			List<Role> savedRoles = this.roleRepo.saveAll(roles);
			savedRoles.forEach(r -> {
				System.out.println(r.getName());
			});
		} catch (Exception e) {
			System.out.println("Something went wrong! This may be due to " + e);
		}
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
