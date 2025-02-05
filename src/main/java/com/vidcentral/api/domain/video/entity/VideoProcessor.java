package com.vidcentral.api.domain.video.entity;

import static com.vidcentral.global.common.util.MediaConstant.*;
import static com.vidcentral.global.error.model.ErrorMessage.*;

import org.springframework.web.multipart.MultipartFile;

import com.vidcentral.global.error.exception.BadRequestException;

public record VideoProcessor(
	MultipartFile originalVideoFile
) {
	public VideoProcessor(MultipartFile originalVideoFile) {
		this.originalVideoFile = validateVideo(originalVideoFile);
	}

	private MultipartFile validateVideo(MultipartFile videoFile) {
		if (videoFile.isEmpty() || videoFile.getSize() > MAX_VIDEO_SIZE ||
			!VideoProperties.isSupportedContentType(videoFile.getContentType())) {

			throw new BadRequestException(FAILED_INVALID_VIDEO_ERROR);
		}

		return videoFile;
	}
}
