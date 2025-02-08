package com.vidcentral.support;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.dto.request.auth.LoginRequest;
import com.vidcentral.api.dto.request.member.SignUpRequest;
import com.vidcentral.api.dto.request.member.UpdateMemberRequest;
import com.vidcentral.api.dto.response.auth.LoginResponse;
import com.vidcentral.api.dto.response.member.MemberInfoResponse;

public class MemberFixture {

	public static Member createMemberEntity() {
		return Member.builder()
			.email("testMember@example.com")
			.password("test123")
			.nickname("testMemberNickname")
			.build();
	}

	public static SignUpRequest createSignUpRequest() {
		return SignUpRequest.builder()
			.email("testMember@example.com")
			.password("test123")
			.checkPassword("test123")
			.nickname("testMemberNickname")
			.build();
	}

	public static LoginRequest createLoginRequest() {
		return LoginRequest.builder()
			.email("testMember@example.com")
			.password("test123")
			.build();
	}

	public static LoginResponse createLoginResponse() {
		return LoginResponse.builder()
			.accessToken("testAccessToken")
			.refreshToken("testRefreshToken")
			.build();
	}

	public static MemberInfoResponse createMemberInfoResponse() {
		return MemberInfoResponse.builder()
			.nickname("testMemberNickname")
			.introduce("testIntroduce")
			.build();
	}

	public static UpdateMemberRequest createUpdateMemberRequest() {
		return UpdateMemberRequest.builder()
			.nickname("testMemberNickname")
			.introduce("testIntroduce")
			.build();
	}
}
