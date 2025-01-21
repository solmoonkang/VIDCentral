package com.vidcentral.api.application.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.member.repository.MemberRepository;
import com.vidcentral.api.dto.request.SignUpRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public void signUpMember(SignUpRequest signUpRequest) {
		validateEmailDuplication(signUpRequest.email());
		validateNicknameDuplication(signUpRequest.nickname());
		validatePasswordMatch(signUpRequest.password(), signUpRequest.checkPassword());

		memberRepository.save(MemberMapper.toMember(signUpRequest, passwordEncoder.encode(signUpRequest.password())));
	}

	private void validateEmailDuplication(String email) {
		if (memberRepository.existsMemberByEmail(email)) {
			throw new IllegalArgumentException("[❎ ERROR] 입력하신 이메일은 이미 사용 중인 이메일입니다.");
		}
	}

	private void validateNicknameDuplication(String nickname) {
		if (memberRepository.existsMemberByNickname(nickname)) {
			throw new IllegalArgumentException("[❎ ERROR] 입력하신 닉네임은 이미 사용 중인 닉네임입니다.");
		}
	}

	private void validatePasswordMatch(String password, String checkPassword) {
		if (!password.equals(checkPassword)) {
			throw new IllegalArgumentException("[❎ ERROR] 입력하신 비밀번호와 일치하지 않습니다.");
		}
	}
}
