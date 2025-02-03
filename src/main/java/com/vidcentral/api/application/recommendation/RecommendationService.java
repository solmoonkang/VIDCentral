package com.vidcentral.api.application.recommendation;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vidcentral.api.application.page.PageMapper;
import com.vidcentral.api.application.video.VideoMapper;
import com.vidcentral.api.domain.like.entity.Like;
import com.vidcentral.api.domain.like.repository.LikeRepository;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.video.entity.VideoTag;
import com.vidcentral.api.domain.video.repository.VideoRepository;
import com.vidcentral.api.domain.viewHistory.repository.ViewHistoryRepository;
import com.vidcentral.api.dto.response.page.PageResponse;
import com.vidcentral.api.dto.response.video.VideoListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendationService {

	private final VideoRepository videoRepository;
	private final ViewHistoryRepository viewHistoryRepository;
	private final LikeRepository likeRepository;

	public Set<VideoTag> extractLikedVideoTags(Member member) {
		return likeRepository.findLikeByMember(member).stream()
			.filter(Like::getIsLike)
			.flatMap(like -> like.getVideo().getVideoTags().stream())
			.collect(Collectors.toSet());
	}

	public Set<String> extractLikedVideoTitle(Member member) {
		return likeRepository.findLikeByMember(member).stream()
			.filter(Like::getIsLike)
			.map(like -> like.getVideo().getTitle())
			.collect(Collectors.toSet());
	}

	public Set<VideoTag> extractViewHistoryVideoTags(Member member) {
		return viewHistoryRepository.findAllByMember(member).stream()
			.flatMap(viewHistory -> viewHistory.getVideo().getVideoTags().stream())
			.collect(Collectors.toSet());
	}

	public PageResponse<VideoListResponse> findRecommendationVideos(Set<VideoTag> likedVideoTags,
		Set<String> likedVideoTitles, Set<VideoTag> viewHistoryVideoTags, Pageable pageable) {

		final Page<Video> allVideos = videoRepository.findAll(pageable);

		final List<VideoListResponse> videoListResponses = allVideos.getContent().stream()
			.filter(video -> isRecommendedVideo(video, likedVideoTags, likedVideoTitles, viewHistoryVideoTags))
			.map(VideoMapper::toVideoListResponse)
			.toList();

		return PageMapper.toPageResponse(
			PageMapper.toPageImpl(videoListResponses, pageable, allVideos.getTotalElements()));
	}

	public boolean isRecommendedVideo(Video video, Set<VideoTag> likedVideoTags, Set<String> likedVideoTitles,
		Set<VideoTag> viewHistoryVideoTags) {

		return hasRelevantTags(video, likedVideoTags, viewHistoryVideoTags) || hasRelevantTitle(video,
			likedVideoTitles);
	}

	private boolean hasRelevantTags(Video video, Set<VideoTag> likedVideoTags, Set<VideoTag> viewHistoryVideoTags) {
		return !Collections.disjoint(video.getVideoTags(), likedVideoTags) ||
			!Collections.disjoint(video.getVideoTags(), viewHistoryVideoTags);
	}

	private boolean hasRelevantTitle(Video video, Set<String> likedVideoTitles) {
		return likedVideoTitles.stream()
			.anyMatch(video.getTitle()::contains);
	}
}
