package com.vidcentral.global.auth;

import com.vidcentral.api.domain.auth.entity.AuthMember;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationThreadLocal {

	private static final ThreadLocal<AuthMember> authMemberHolder = new ThreadLocal<>();

	public static void saveAuthMemberHolder(AuthMember authMember) {
		AuthenticationThreadLocal.authMemberHolder.set(authMember);
	}

	public static AuthMember searchAuthMemberHolder() {
		return authMemberHolder.get();
	}

	public static void removeAuthMemberHolder() {
		authMemberHolder.remove();
	}
}
