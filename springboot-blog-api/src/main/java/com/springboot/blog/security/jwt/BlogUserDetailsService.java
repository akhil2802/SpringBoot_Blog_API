//package com.springboot.blog.security.jwt;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.springboot.blog.repositories.UserRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class BlogUserDetailsService implements UserDetailsService {
//	
//	private final UserRepository userRepo;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		return userRepo.findByEmail(username).map(BlogUserDetails::new)
//				.orElseThrow(() -> new UsernameNotFoundException("User Not found!"));
//	}
//
//}
