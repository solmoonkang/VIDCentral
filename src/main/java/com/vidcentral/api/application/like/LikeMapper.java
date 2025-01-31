package com.vidcentral.api.application.like;

import com.vidcentral.api.domain.like.entity.Like;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeMapper {

	public static Like toLike(Video video, Member member, boolean isLike) {
		return Like.builder()
			.video(video)
			.member(member)
			.isLike(isLike)
			.build();
	}
}
