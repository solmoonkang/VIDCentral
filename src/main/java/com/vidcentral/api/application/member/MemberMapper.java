package com.vidcentral.api.application.member;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.dto.request.SignUpRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberMapper {

	public static Member toMember(SignUpRequest signUpRequest, String encodedPassword) {
		return Member.builder()
			.email(signUpRequest.email())
			.password(encodedPassword)
			.nickname(signUpRequest.nickname())
			.build();
	}
}
