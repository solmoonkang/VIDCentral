package com.vidcentral.api.dto.request.video;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SearchVideoRequest(
	@NotBlank(message = "검색 키워드는 필수 입력 항목입니다.")
	String keyword
) {
}
