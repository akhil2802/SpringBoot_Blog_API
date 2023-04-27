//package com.springboot.blog.security.jwt;
//
//import java.security.Principal;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.springboot.blog.entities.User;
//import com.springboot.blog.exception.ApiException;
//import com.springboot.blog.payloads.JwtAuthResponse;
//import com.springboot.blog.payloads.UserDto;
//import com.springboot.blog.repositories.UserRepository;
//import com.springboot.blog.services.UserService;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/v1/auth")
//public class JWTController {
//	
//	private final JWTService jwtService;
//	private final AuthenticationManager authManager;
//	private final UserService userService;
//	private final UserDetailsService userDetailsService;
//	private final ModelMapper modelMapper;
//	private final UserRepository userRepo;
//	
//	@PostMapping("/login")
//	public ResponseEntity<JwtAuthResponse> getTokenForAuthenticatedUser(@RequestBody JWTAuthRequest authRequest) {
//		
//		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//		
//		if(auth.isAuthenticated()) {
//			
//			UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
//		
//			JwtAuthResponse authResponse = new JwtAuthResponse();
//			String generatedToken = jwtService.getGeneratedToken(authRequest.getUsername());
//			authResponse.setToken(generatedToken);
//			authResponse.setUser(this.modelMapper.map((User) userDetails, UserDto.class));
//		
//			return new ResponseEntity<JwtAuthResponse>(authResponse, HttpStatus.OK);
//		
//		} else {
//			throw new ApiException("Invalid user credentials!");
//		}
//	}
//	
//	// REGISTER NEW USER:
//	
//	@PostMapping("/register")
//	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
//		UserDto createdNewUser = this.userService.registerNewUser(userDto);
//		return new ResponseEntity<UserDto>(createdNewUser, HttpStatus.CREATED);
//	}
//	
//	// get loggedin user data
//
//	@GetMapping("/current-user/")
//	public ResponseEntity<UserDto> getUser(Principal principal) {
//		User user = this.userRepo.findByEmail(principal.getName()).get();
//		return new ResponseEntity<UserDto>(this.modelMapper.map(user, UserDto.class), HttpStatus.OK);
//	}
//		
//}
