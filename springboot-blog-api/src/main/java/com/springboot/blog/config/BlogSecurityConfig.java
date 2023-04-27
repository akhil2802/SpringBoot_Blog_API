//
//package com.springboot.blog.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import com.springboot.blog.security.jwt.BlogUserDetailsService;
//import com.springboot.blog.security.jwt.JWTAuthenticationFilter;
//import com.springboot.blog.security.jwt.JWTAuthenticationEntryPoint;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class BlogSecurityConfig {
//
//	@Autowired
//	private JWTAuthenticationEntryPoint jwtAuthEntryPoint;
//
//	@Autowired
//	private JWTAuthenticationFilter jwtAuthFilter;
//
//	@Autowired
//	private BlogUserDetailsService blogUserDetailsService;
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		
//	   return http 
//			  .csrf() 
//			  .disable() 
//			  .authorizeHttpRequests()
//			  .requestMatchers(AppConstants.UN_SECURED_URLS)
//			  .permitAll()
//			  .requestMatchers(HttpMethod.GET)
//			  .permitAll()
//			  .and()
//			  .authorizeHttpRequests()
//			  .requestMatchers(AppConstants.SECURED_URLS).hasAuthority("ADMIN")
//			  .anyRequest() 
//			  .authenticated() 
//			  .and()
//			  .exceptionHandling()
//			  .authenticationEntryPoint(this.jwtAuthEntryPoint) 
//			  .and()
//			  .sessionManagement() 
//			  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			  .and()
//			  .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//			  .authenticationProvider(authenticationProvider())
//			  .build();
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		var authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(this.blogUserDetailsService);
//		authProvider.setPasswordEncoder(passwordEncoder());
//		return authProvider;
//	}
//
//	@Bean
//	public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
//		return authConfig.getAuthenticationManager();
//	}
//	
//	@Bean
//    public FilterRegistrationBean<CorsFilter> coresFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.addAllowedOriginPattern("*");
//        corsConfiguration.addAllowedHeader("Authorization");
//        corsConfiguration.addAllowedHeader("Content-Type");
//        corsConfiguration.addAllowedHeader("Accept");
//        corsConfiguration.addAllowedMethod("POST");
//        corsConfiguration.addAllowedMethod("GET");
//        corsConfiguration.addAllowedMethod("DELETE");
//        corsConfiguration.addAllowedMethod("PUT");
//        corsConfiguration.addAllowedMethod("OPTIONS");
//        corsConfiguration.setMaxAge(3600L);
//
//        source.registerCorsConfiguration("/**", corsConfiguration);
//
//        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
//
//        bean.setOrder(-110);
//
//        return bean;
//    }
//
//}
