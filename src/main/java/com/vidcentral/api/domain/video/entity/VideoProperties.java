package com.vidcentral.api.domain.video.entity;

import lombok.Getter;

@Getter
public enum VideoProperties {

	DEFAULT_VIDEO(100 * 1024 * 1024, "video/mp4");

	private final long maxSize;
	private final String contentType;

	VideoProperties(long maxSize, String contentType) {
		this.maxSize = maxSize;
		this.contentType = contentType;
	}
}
