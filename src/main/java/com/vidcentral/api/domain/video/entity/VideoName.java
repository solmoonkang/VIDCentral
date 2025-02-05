package com.vidcentral.api.domain.video.entity;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoName {

	private final String fileName;

	public static VideoName createFromMultipartFile(MultipartFile multipartFile) {
		final String originalFileName = multipartFile.getOriginalFilename();
		final String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
		final String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8);

		return new VideoName(
			String.format("%s_%s%s", encodedFileName, UUID.randomUUID(), fileExtension));
	}
}
