package com.vidcentral.api.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UploadCommentRequest(
	@NotBlank(message = "댓글 내용은 필수 입력 항목입니다.")
	String content
) {
}
