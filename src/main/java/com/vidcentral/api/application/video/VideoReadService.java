package com.vidcentral.api.application.video;

import static com.vidcentral.global.error.model.ErrorMessage.*;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.video.entity.VideoTag;
import com.vidcentral.api.domain.video.repository.VideoRepository;
import com.vidcentral.global.error.exception.BadRequestException;
import com.vidcentral.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoReadService {

	private static final int MAX_TAG_COUNT = 3;

	private final VideoRepository videoRepository;

	public Video findVideo(Long videoId) {
		return videoRepository.findById(videoId)
			.orElseThrow(() -> new NotFoundException(FAILED_VIDEO_NOT_FOUND_ERROR));
	}

	public Page<Video> findAllVideos(Pageable pageable) {
		return videoRepository.findAll(pageable);
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
