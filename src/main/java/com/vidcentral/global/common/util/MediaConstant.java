package com.vidcentral.global.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MediaConstant {

	public static final String PROFILE_IMAGE_PATH = "profile-images/";
	public static final String IMAGE_DOMAIN = "https://image.vidcentral.com/";
	public static final String MEMBER_PROFILE_URL = "vidcentral/default/members-profile.png";
	public static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024;

	public static final String VIDEO_PATH = "videos/";
	public static final long MAX_VIDEO_SIZE = 10 * 1024 * 1024;
}
