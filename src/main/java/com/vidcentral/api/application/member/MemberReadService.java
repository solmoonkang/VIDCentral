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

	public Optional<Member> findMember(String email) {
		return memberRepository.findMemberByEmail(email);
	}
}
