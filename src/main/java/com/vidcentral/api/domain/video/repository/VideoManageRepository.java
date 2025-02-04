package com.vidcentral.api.domain.video.repository;

import static com.vidcentral.global.common.util.RedisConstant.*;

import java.time.Duration;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.vidcentral.api.infrastructure.redis.HashRedisRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VideoManageRepository {

	private final HashRedisRepository hashRedisRepository;

	public void incrementViews(Long videoId) {
		hashRedisRepository.save(REDIS_VIDEO_VIEW_PREFIX + videoId, 1L, Duration.ofMinutes(VIEWS_EXPIRE_MINUTES));
	}

	public Set<String> findAllKeys(String pattern) {
		return hashRedisRepository.getAllKeys(pattern);
	}

	public Long findViews(Long videoId) {
		return (Long)hashRedisRepository.get(REDIS_VIDEO_VIEW_PREFIX + videoId);
	}

	public void deleteViews(Long videoId) {
		hashRedisRepository.delete(REDIS_VIDEO_VIEW_PREFIX + videoId);
	}
}
