package com.vidcentral.api.application.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.member.repository.MemberRepository;
import com.vidcentral.api.dto.request.member.SignUpRequest;
import com.vidcentral.api.dto.request.member.UpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public void signUpMember(SignUpRequest signUpRequest) {
		memberRepository.save(MemberMapper.toMember(signUpRequest, passwordEncoder.encode(signUpRequest.password())));
	}

	public void updateMemberInfo(Member member, UpdateRequest updateRequest) {
		member.updateIntroduce(updateRequest.introduce());
		member.updateProfileImageURL(updateRequest.profileImageURL());
		member.updateNickname(updateRequest.nickname());
	}
}
