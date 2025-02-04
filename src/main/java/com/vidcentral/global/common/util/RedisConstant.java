package com.vidcentral.global.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisConstant {

	public static final String REDIS_REFRESH_TOKEN_PREFIX = "refreshToken:";
	public static final String REDIS_VIDEO_VIEW_PREFIX = "videoView:";
	public static final String REDIS_SEARCH_HISTORY_PREFIX = "searchHistory:";

	public static final int TOKEN_EXPIRE_MINUTES = 20_160;
	public static final int VIEWS_EXPIRE_MINUTES = 5;
	public static final int SEARCH_EXPIRE_MINUTES = 43_200;
}
