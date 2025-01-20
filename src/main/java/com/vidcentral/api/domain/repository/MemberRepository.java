package com.vidcentral.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidcentral.api.domain.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
