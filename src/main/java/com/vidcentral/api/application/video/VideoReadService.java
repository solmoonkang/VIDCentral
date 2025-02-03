package com.vidcentral.api.application.video;

import static com.vidcentral.global.error.model.ErrorMessage.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vidcentral.api.application.member.MemberReadService;
import com.vidcentral.api.application.recommendation.RecommendationService;
import com.vidcentral.api.application.viewHistory.ViewHistoryService;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.video.entity.VideoTag;
import com.vidcentral.api.domain.video.repository.VideoRepository;
import com.vidcentral.api.dto.response.video.VideoListResponse;
import com.vidcentral.api.dto.response.viewHistory.ViewHistoryListResponse;
import com.vidcentral.global.error.exception.BadRequestException;
import com.vidcentral.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoReadService {

	private static final int MAX_TAG_COUNT = 3;

	private final MemberReadService memberReadService;
	private final ViewHistoryService viewHistoryService;
	private final RecommendationService recommendationService;
	private final VideoRepository videoRepository;

	public Video findVideo(Long videoId) {
		return videoRepository.findById(videoId)
			.orElseThrow(() -> new NotFoundException(FAILED_VIDEO_NOT_FOUND_ERROR));
	}

	public Page<Video> findAllVideos(Pageable pageable) {
		return videoRepository.findAll(pageable);
	}

	public List<Video> findAllVideosByTitle(String title) {
		return videoRepository.findVideosByTitle(title);
	}

	public List<Video> findAllVideosByDescription(String description) {
		return videoRepository.findVideosByDescription(description);
	}

	public void saveViewHistory(AuthMember authMember, Video video) {
		final Member loginMember = memberReadService.findMember(authMember.email());
		viewHistoryService.saveViewHistory(loginMember, video);
	}

	public List<ViewHistoryListResponse> findAllViewHistory(AuthMember authMember) {
		return Optional.ofNullable(authMember)
			.map(viewHistoryService::searchAllViewHistory)
			.orElseThrow(() -> new BadRequestException(FAILED_INVALID_REQUEST_ERROR));
	}

	public List<VideoListResponse> findAllRecommendationVideos(Member member) {
		final Set<VideoTag> likedVideoTags = recommendationService.extractLikedVideoTags(member);
		final Set<String> likedVideoTitles = recommendationService.extractLikedVideoTitle(member);
		final Set<VideoTag> viewHistoryVideoTags = recommendationService.extractViewHistoryVideoTags(member);

		return recommendationService.findRecommendationVideos(likedVideoTags, likedVideoTitles, viewHistoryVideoTags);
	}

	public void validateMemberHasAccess(String videoOwnerEmail, String authMemberEmail) {
		if (!videoOwnerEmail.equals(authMemberEmail)) {
			throw new BadRequestException(FAILED_INVALID_VIDEO_ACCESS_ERROR);
		}
	}

	public void validateTagCount(Set<VideoTag> videoTags) {
		if (videoTags == null || videoTags.size() > MAX_TAG_COUNT) {
			throw new BadRequestException(FAILED_MAX_TAG_COUNT_ERROR);
		}
	}
}
