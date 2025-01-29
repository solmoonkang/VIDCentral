package com.vidcentral.api.application.auth;

import static com.vidcentral.global.common.util.TokenConstant.*;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vidcentral.global.common.util.CookieUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AnonymousMemberService {

	public String getOrCreateAnonymousId(HttpServletResponse httpServletResponse, String existingAnonymousId) {
		if (existingAnonymousId.isEmpty()) return createAnonymousId(httpServletResponse);
		return existingAnonymousId;
	}

	private String createAnonymousId(HttpServletResponse httpServletResponse) {
		String anonymousId = UUID.randomUUID().toString();

		Cookie anonymousCookie = CookieUtils.generateAnonymousIdCookie(ANONYMOUS_ID_COOKIE, anonymousId);
		httpServletResponse.addCookie(anonymousCookie);

		return anonymousId;
	}
}
