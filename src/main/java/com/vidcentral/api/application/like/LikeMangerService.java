package com.vidcentral.api.application.like;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vidcentral.api.application.member.MemberReadService;
import com.vidcentral.api.application.video.VideoReadService;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.like.entity.Like;
import com.vidcentral.api.domain.like.repository.LikeRepository;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeMangerService {

	private final MemberReadService memberReadService;
	private final VideoReadService videoReadService;
	private final LikeRepository likeRepository;

	@Transactional
	public void likeVideo(AuthMember authMember, Long videoId) {
		final Member loginMember = memberReadService.findMember(authMember.email());
		final Video video = videoReadService.findVideo(videoId);

		final Like existingLike = likeRepository.findLikeByVideoAndMember(video, loginMember)
			.orElseGet(() -> createLikeOrDislike(video, loginMember, true));

		existingLike.updateIsLike(true);
		likeRepository.save(existingLike);
	}

	@Transactional
	public void dislikeVideo(AuthMember authMember, Long videoId) {
		final Member loginMember = memberReadService.findMember(authMember.email());
		final Video video = videoReadService.findVideo(videoId);

		final Like existingLike = likeRepository.findLikeByVideoAndMember(video, loginMember)
			.orElseGet(() -> createLikeOrDislike(video, loginMember, false));

		existingLike.updateIsLike(false);
		likeRepository.save(existingLike);
	}

	private Like createLikeOrDislike(Video video, Member member, boolean isLike) {
		final Like likeOrDislike = LikeMapper.toLike(video, member, isLike);
		return likeRepository.save(likeOrDislike);
	}
}
