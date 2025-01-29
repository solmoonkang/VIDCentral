package com.vidcentral.api.application.video;

import static com.vidcentral.global.error.model.ErrorMessage.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vidcentral.api.application.member.MemberReadService;
import com.vidcentral.api.application.viewHistory.SessionViewHistoryService;
import com.vidcentral.api.application.viewHistory.ViewHistoryService;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.video.entity.VideoTag;
import com.vidcentral.api.domain.video.repository.VideoRepository;
import com.vidcentral.api.dto.response.viewHistory.ViewHistoryListResponse;
import com.vidcentral.global.error.exception.BadRequestException;
import com.vidcentral.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoReadService {

	private static final int MAX_TAG_COUNT = 3;

	private final VideoRepository videoRepository;
	private final MemberReadService memberReadService;
	private final ViewHistoryService viewHistoryService;
	private final SessionViewHistoryService sessionViewHistoryService;

	public Video findVideo(Long videoId) {
		return videoRepository.findById(videoId)
			.orElseThrow(() -> new NotFoundException(FAILED_VIDEO_NOT_FOUND_ERROR));
	}

	public List<Video> findAllVideos() {
		return videoRepository.findAll();
	}

	public void saveViewHistory(AuthMember authMember, Video video, String anonymousId) {
		Optional.ofNullable(authMember)
			.map(loginMember -> memberReadService.findMember(loginMember.email()))
			.ifPresentOrElse(
				loginMember -> viewHistoryService.saveViewHistory(loginMember, video),
				() -> sessionViewHistoryService.addSessionViewHistory(anonymousId, video)
			);
	}

	public List<ViewHistoryListResponse> searchAllViewHistory(AuthMember authMember, String anonymousId) {
		return Optional.ofNullable(authMember)
			.map(viewHistoryService::searchAllViewHistoryForLoggedInMember)
			.orElseGet(() -> viewHistoryService.searchAllViewHistoryForAnonymousMember(anonymousId));
	}

	public void validateMemberHasAccess(String videoOwnerEmail, String authMemberEmail) {
		if (!videoOwnerEmail.equals(authMemberEmail)) {
			throw new BadRequestException(FAILED_INVALID_VIDEO_ACCESS_ERROR);
		}
	}

	public void validateTagCount(List<VideoTag> videoTags) {
		if (videoTags == null || videoTags.size() > MAX_TAG_COUNT) {
			throw new BadRequestException(FAILED_MAX_TAG_COUNT_ERROR);
		}
	}
}
