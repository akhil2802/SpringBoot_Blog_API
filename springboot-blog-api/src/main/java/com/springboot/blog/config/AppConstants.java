package com.springboot.blog.config;

public class AppConstants {

	public static final String PAGE_NUMBER = "0";
	public static final String PAGE_SIZE = "5";
	public static final String POST_SORT_BY = "postId";
	public static final String USER_SORT_BY = "id";
	public static final String CATEGORY_SORT_BY = "categoryId";
	public static final String COMMENT_SORT_BY = "id";
	public static final String SORT_ORDER = "asc";
	
	public static final String[] SECURED_URLS = { "/api/v1/**" };
	
//	public static final String[] UN_SECURED_URLS = { "/api/v1/auth/**", "/api/v1/users", "/api/v1/users/{userId}",
//			"/api/v1/posts", "/api/v1/posts/{postId}", "/api/v1/categories", "/api/v1/posts/categories/{categoryId}",
//			"/api/v1/posts/category/{categoryId}", "/api/v1/posts/user/{userId}/category/{categoryId}", "/v2/api-docs/**", "/v3/api-docs", "/swagger-resources/**",
//			"/v3/swagger-ui/**", "/webjars/**", "/v3/swagger-ui/index.html" };
	
	public static final String[] UN_SECURED_URLS = { "/api/v1/auth/**", "/v3/api-docs", "/v2/api-docs",
            "/swagger-resources/**", "/swagger-ui/**", "/webjars/**" };
	
//	public static final long JWT_TOKEN_VALIDITY = 1000 + 60 * 30;         // valid for 30 minutes
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;     // valid for 1 hour
	public static final String JWT_SECRET = "7234753778214125442A472D4B6150645367566B59703373357638792F423F45";
	public static final Integer ADMIN_USER = 501;
	public static final Integer NORMAL_USER = 502;
	public static final String AUTHORIZATION_HEADER = "Authorization";
}
