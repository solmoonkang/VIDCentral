package com.vidcentral.api.application.comment;

import com.vidcentral.api.domain.comment.entity.Comment;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.dto.response.comment.CommentListResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

	public static Comment toComment(Member member, Video video, String content) {
		return Comment.builder()
			.member(member)
			.video(video)
			.content(content)
			.build();
	}

	public static CommentListResponse toCommentListResponse(Comment comment) {
		return CommentListResponse.builder()
			.nickname(comment.getMember().getNickname())
			.content(comment.getContent())
			.build();
	}
}
