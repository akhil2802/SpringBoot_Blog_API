package com.springboot.blog.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllResponse {
	
	private List<?> content;
	private int pageNumber;
	private int pageSize;
	private int numberOfElements;
	private long totalNumberOfElements;
	private int totalPages;
	private boolean lastPage;
}
