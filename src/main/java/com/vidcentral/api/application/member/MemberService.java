package com.vidcentral.api.application.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vidcentral.api.dto.request.SignUpRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberWriteService memberWriteService;

	@Transactional
	public void signUpMember(SignUpRequest signUpRequest) {
		memberWriteService.signUpMember(signUpRequest);
	}
}
