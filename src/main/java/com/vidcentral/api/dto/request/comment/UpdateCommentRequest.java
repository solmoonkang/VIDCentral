package com.vidcentral.api.dto.request.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Schema(description = "댓글 수정 요청")
public record UpdateCommentRequest(
	@NotBlank(message = "댓글 내용은 필수 입력 항목입니다.")
	@Schema(description = "댓글 내용", example = "commentContent")
	String content
) {
}
