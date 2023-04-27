//package com.springboot.blog.security.jwt;
//
//import java.io.IOException;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class JWTAuthenticationFilter extends OncePerRequestFilter {
//
//	private final JWTService jwtService;
//	private final BlogUserDetailsService blogUserDetailsService;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//
//		String token = null;
//		String username = null;
//		String authHeader = request.getHeader("Authorization");
//
//		if (authHeader != null && authHeader.startsWith("Bearer ")) {
//			token = authHeader.substring(7);
//			try {
//				username = jwtService.extractUsernameFromToken(token);
//			} catch (IllegalArgumentException e) {
//				System.out.println("Unable to get Jwt token");
//			} catch (ExpiredJwtException e) {
//				System.out.println("Jwt token has expired");
//			} catch (MalformedJwtException e) {
//				System.out.println("invalid jwt");
//			} 
//		} else {
//			System.out.println("Jwt token does not begin with Bearer");
//		}
//		
//		// Once we get the token, now validate it:
//
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//			UserDetails userDetails = blogUserDetailsService.loadUserByUsername(username);
//
//			if (jwtService.validateToken(token, userDetails)) {
//				var authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
//						userDetails.getAuthorities());
//				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(authToken);
//			} else {
//				System.out.println("Invalid JWT Token!");
//			}
//		} else {
//			System.out.println("Username is null or Context is not null");
//		}
//
//		filterChain.doFilter(request, response);
//	}
//
//}
