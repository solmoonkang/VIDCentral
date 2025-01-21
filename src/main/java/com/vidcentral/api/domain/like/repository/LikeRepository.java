package com.vidcentral.api.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidcentral.api.domain.like.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
