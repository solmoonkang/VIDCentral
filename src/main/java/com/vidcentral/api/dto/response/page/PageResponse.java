package com.vidcentral.api.dto.response.page;

import java.util.List;

import lombok.Builder;

@Builder
public record PageResponse<T>(
	List<T> content,
	int pageIndex,
	int totalPages,
	long totalElements,
	boolean isLast
) {
}
