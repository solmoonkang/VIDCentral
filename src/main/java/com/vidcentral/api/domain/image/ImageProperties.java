package com.vidcentral.api.domain.image;

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

}
