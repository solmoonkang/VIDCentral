package com.vidcentral.api.application.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vidcentral.api.application.auth.AuthorizationService;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.dto.request.auth.LoginRequest;
import com.vidcentral.api.dto.request.member.SignUpRequest;
import com.vidcentral.api.dto.request.member.UpdateRequest;
import com.vidcentral.api.dto.response.auth.LoginResponse;
import com.vidcentral.api.dto.response.member.MemberInfoResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberWriteService memberWriteService;
	private final MemberReadService memberReadService;
	private final AuthorizationService authorizationService;

	@Transactional
	public void signUpMember(SignUpRequest signUpRequest) {
		memberReadService.validateEmailDuplication(signUpRequest.email());
		memberReadService.validateNicknameDuplication(signUpRequest.nickname());
		memberReadService.validatePasswordMatch(signUpRequest.password(), signUpRequest.checkPassword());
		memberWriteService.signUpMember(signUpRequest);
	}

	@Transactional
	public LoginResponse loginMember(HttpServletResponse httpServletResponse, LoginRequest loginRequest) {
		final Member loginMember = memberReadService.findMember(loginRequest.email());
		memberReadService.validateLoginPasswordMatch(loginRequest.password(), loginMember.getPassword());

		return authorizationService
			.issueServiceToken(httpServletResponse, loginMember.getEmail(), loginMember.getNickname());
	}

	public MemberInfoResponse searchMemberInfo(Long memberId) {
		final Member loginMember = memberReadService.readMember(memberId);
		return MemberMapper.toMemberInfoResponse(loginMember);
	}

	@Transactional
	public void updateMemberInfo(AuthMember authMember, UpdateRequest updateRequest) {
		final Member loginMember = memberReadService.findMember(authMember.email());
		memberReadService.validateNicknameDuplication(updateRequest.nickname());
		memberReadService.validateProfileImageURLExtension(updateRequest.profileImageURL());

		memberWriteService.updateMemberInfo(loginMember, updateRequest);
	}
}
