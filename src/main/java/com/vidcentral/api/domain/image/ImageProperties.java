package com.vidcentral.api.domain.image;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum ImageProperties {

	PROFILE_IMAGE(150, "image/png");

	private final int width;
	private final String contentType;

	ImageProperties(int width, String contentType) {
		this.width = width;
		this.contentType = contentType;
	}

	public static boolean isSupportedContentType(String contentType) {
		return Arrays.stream(values())
			.anyMatch(imageProperties -> imageProperties.contentType.equals(contentType));
	}
}
