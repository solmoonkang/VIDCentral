package com.vidcentral.global.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageURL {

	public static final String JPG = ".jpg";
	public static final String JPEG = ".jpeg";
	public static final String PNG = ".png";

	public static final String DEFAULT_IMAGE_DOMAIN = "https://image.vidcentral.com/";
	public static final String MEMBER_PROFILE_URL = "default/member-profile.png";
}
