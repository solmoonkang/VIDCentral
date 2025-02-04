package com.vidcentral.api.domain.history.repository;

import static com.vidcentral.global.common.util.GlobalConstant.*;
import static com.vidcentral.global.common.util.RedisConstant.*;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.vidcentral.api.infrastructure.redis.HashRedisRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HistoryManageRepository {

	private final HashRedisRepository hashRedisRepository;

	public void saveSearchHistory(String email, String keyword) {
		hashRedisRepository.save(REDIS_SEARCH_HISTORY_PREFIX + email, keyword,
			Duration.ofMinutes(SEARCH_EXPIRE_MINUTES));
	}

	public List<String> findSearchHistories(String email) {
		return (List<String>)hashRedisRepository.get(REDIS_SEARCH_HISTORY_PREFIX + email);
	}

	public Set<String> findAllMemberEmails() {
		return hashRedisRepository.getAllKeys(REDIS_SEARCH_HISTORY_PREFIX + WILDCARD);
	}

	public void deleteSearchHistory(String email) {
		hashRedisRepository.delete(REDIS_SEARCH_HISTORY_PREFIX + email);
	}
}
