package com.vidcentral.api.dto.response.page;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "페이지네이션 응답")
public record PageResponse<T>(
	@Schema(description = "현재 페이지의 콘텐츠 목록")
	List<T> content,

	@Schema(description = "현재 페이지 인덱스 (0부터 시작)", example = "0")
	int pageIndex,

	@Schema(description = "총 페이지 수", example = "10")
	int totalPages,

	@Schema(description = "총 요소 수", example = "100")
	long totalElements,

	@Schema(description = "마지막 페이지 여부", example = "true")
	boolean isLast
) {
}
