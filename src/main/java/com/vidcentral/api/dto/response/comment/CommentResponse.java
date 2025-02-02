package com.vidcentral.api.dto.response.comment;

import lombok.Builder;

@Builder
public record CommentResponse(
	String nickname,
	String content
) {
}
