package com.vidcentral.api.domain.video.entity;

import static com.vidcentral.global.common.util.GlobalConstant.*;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoName {

	private static final String VIDEO_PATH = "video" + DELIMITER;

	private final String fileName;

	public static VideoName createFromMultipartFile(MultipartFile multipartFile, VideoProperties videoProperties) {
		return switch (videoProperties) {
			case DEFAULT_VIDEO ->
				new VideoName(VIDEO_PATH + multipartFile.getOriginalFilename() + "_" + UUID.randomUUID() + VIDEO_EXTENSION);
		};
	}
}
