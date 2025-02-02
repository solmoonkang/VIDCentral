package com.vidcentral.api.dto.response.comment;

import lombok.Builder;

@Builder
public record CommentListResponse(
	String nickname,
	String content
) {
}
