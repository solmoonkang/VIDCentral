package com.vidcentral.global.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenConstant {

	public static final String MEMBER_EMAIL = "email";
	public static final String MEMBER_NICKNAME = "nickname";

	public static final String BEARER = "Bearer";

	public static final String ACCESS_TOKEN_HEADER = "Authorization";
	public static final String REFRESH_TOKEN_COOKIE = "Authorization_RefreshToken";
	public static final String ANONYMOUS_ID_COOKIE = "Anonymous_Id";

	public static final int EXPIRE_DAYS = 14;
}
