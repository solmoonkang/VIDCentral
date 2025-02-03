package com.vidcentral.api.application.page;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.vidcentral.api.dto.response.page.PageResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageMapper {

	public static <T> PageResponse<T> toPageResponse(Page<T> page) {
		return PageResponse.<T>builder()
			.content(page.getContent())
			.pageIndex(page.getNumber())
			.totalPages(page.getTotalPages())
			.totalElements(page.getTotalElements())
			.isLast(page.isLast())
			.build();
	}

	public static <T> Page<T> toPageImpl(List<T> content, Pageable pageable, long total) {
		return new PageImpl<>(content, pageable, total);
	}
}
