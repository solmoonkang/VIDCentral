package com.vidcentral.api.domain.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidcentral.api.domain.like.entity.Like;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	Optional<Like> findLikeByVideoAndMember(Video video, Member member);
}
