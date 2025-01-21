package com.vidcentral.api.application.member;

import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.member.repository.MemberRepository;
import com.vidcentral.global.error.exception.NotFoundException;
import com.vidcentral.global.error.model.ErrorMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberReadService {

	private final MemberRepository memberRepository;

	public Member readMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.FAILED_MEMBER_NOT_FOUND));
	}

	public Member findMember(String email) {
		return memberRepository.findMemberByEmail(email)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.FAILED_MEMBER_NOT_FOUND));
	}
}
