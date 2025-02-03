package com.vidcentral.api.dto.response.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "댓글 목록 응답")
public record CommentListResponse(
	@Schema(description = "사용자 닉네임", example = "memberNickname")
	String nickname,

	@Schema(description = "댓글 내용", example = "commentContent")
	String content
) {
}
