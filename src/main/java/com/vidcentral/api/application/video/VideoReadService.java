package com.vidcentral.api.application.video;

import static com.vidcentral.global.error.model.ErrorMessage.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.video.repository.VideoRepository;
import com.vidcentral.global.error.exception.BadRequestException;
import com.vidcentral.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoReadService {

	private final VideoRepository videoRepository;

	public Video findVideo(Long videoId) {
		return videoRepository.findById(videoId)
			.orElseThrow(() -> new NotFoundException(FAILED_VIDEO_NOT_FOUND_ERROR));
	}

	public List<Video> findAllVideos() {
		return videoRepository.findAll();
	}

	public void validateMemberHasAccess(String videoOwnerEmail, String authMemberEmail) {
		if (!videoOwnerEmail.equals(authMemberEmail)) {
			throw new BadRequestException(FAILED_INVALID_VIDEO_ACCESS_ERROR);
		}
	}
}
