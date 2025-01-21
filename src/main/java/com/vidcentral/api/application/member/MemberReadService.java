package com.vidcentral.api.application.member;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberReadService {

	private final MemberRepository memberRepository;

	public Member readMember(String email) {
		return memberRepository.findMemberByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("[❎ ERROR] 요청하신 사용자를 찾을 수 없습니다."));
	}

	public Optional<Member> findMember(String email) {
		return memberRepository.findMemberByEmail(email);
	}
}
