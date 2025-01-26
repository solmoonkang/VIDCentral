package com.vidcentral.api.domain.video.entity;

import static com.vidcentral.global.error.model.ErrorMessage.*;

import org.springframework.web.multipart.MultipartFile;

import com.vidcentral.global.error.exception.BadRequestException;

public record VideoProcessor(
	MultipartFile originalVideoFile
) {

	private static final String VIDEO_FORMAT_PREFIX = "video/";
	private static final int MAX_VIDEO_SIZE = 10 * 1024 * 1024;

	public VideoProcessor(MultipartFile originalVideoFile) {
		this.originalVideoFile = validateVideo(originalVideoFile);
	}

	private MultipartFile validateVideo(MultipartFile videoFile) {
		if (videoFile.isEmpty() || videoFile.getSize() > MAX_VIDEO_SIZE || !videoFile.getContentType()
			.startsWith(VIDEO_FORMAT_PREFIX)) {
			throw new BadRequestException(FAILED_INVALID_VIDEO_ERROR);
		}

		return videoFile;
	}
}
