package com.vidcentral.api.application.video;

import static com.vidcentral.global.error.model.ErrorMessage.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vidcentral.api.application.page.PageMapper;
import com.vidcentral.api.application.recommendation.RecommendationService;
import com.vidcentral.api.application.viewHistory.ViewHistoryService;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.video.entity.VideoTag;
import com.vidcentral.api.domain.video.repository.VideoRepository;
import com.vidcentral.api.dto.request.video.SearchVideoRequest;
import com.vidcentral.api.dto.response.page.PageResponse;
import com.vidcentral.api.dto.response.video.VideoListResponse;
import com.vidcentral.api.dto.response.viewHistory.ViewHistoryListResponse;
import com.vidcentral.global.error.exception.BadRequestException;
import com.vidcentral.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoReadService {

	private static final int MAX_TAG_COUNT = 3;

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

	public PageResponse<VideoListResponse> findAllVideosByKeyword(SearchVideoRequest searchVideoRequest, Pageable pageable) {
		final Set<Video> distinctVideos = findDistinctVideosByKeyword(searchVideoRequest.keyword(), pageable);

		final List<VideoListResponse> videoListResponses = distinctVideos.stream()
			.map(VideoMapper::toVideoListResponse)
			.toList();

		return PageMapper.toPageResponse(PageMapper.toPageImpl(videoListResponses, pageable, distinctVideos.size()));
	}

	private Set<Video> findDistinctVideosByKeyword(String keyword, Pageable pageable) {
		final Page<Video> videosFoundByTitle = findAllVideosByTitle(keyword, pageable);
		final Page<Video> videosFoundByDescription = findAllVideosByDescription(keyword, pageable);

		final Set<Video> distinctVideos = new HashSet<>();
		distinctVideos.addAll(videosFoundByTitle.getContent());
		distinctVideos.addAll(videosFoundByDescription.getContent());

		return distinctVideos;
	}

	private Page<Video> findAllVideosByTitle(String title, Pageable pageable) {
		return videoRepository.findVideosByTitle(title, pageable);
	}

	private Page<Video> findAllVideosByDescription(String description, Pageable pageable) {
		return videoRepository.findVideosByDescription(description, pageable);
	}

	public PageResponse<ViewHistoryListResponse> findAllViewHistory(AuthMember authMember, Pageable pageable) {
		return Optional.ofNullable(authMember)
			.map(loginMember -> viewHistoryService.searchAllViewHistory(loginMember, pageable))
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
