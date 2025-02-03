package com.vidcentral.api.infrastructure.redis;

import static com.vidcentral.global.error.model.ErrorMessage.*;

import java.time.Duration;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Repository;

import com.vidcentral.global.error.exception.NotFoundException;

@Repository
public class HashRedisRepository {

	private final RedisTemplate<String, Object> redisTemplate;
	private final HashOperations<String, String, Object> hashOperations;
	private final Jackson2HashMapper jackson2HashMapper;

	public HashRedisRepository(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
		hashOperations = redisTemplate.opsForHash();
		jackson2HashMapper = new Jackson2HashMapper(false);
	}

	public void save(String key, Object value, Duration timeout) {
		hashOperations.putAll(key, new Jackson2HashMapper(false).toHash(value));
		redisTemplate.expire(key, timeout);
	}

	public Object get(String key) {
		final Map<String, Object> memberDataMap = hashOperations.entries(key);
		validateTokenEmpty(memberDataMap);
		return jackson2HashMapper.fromHash(memberDataMap);
	}

	public void delete(String key) {
		redisTemplate.delete(key);
	}

	private void validateTokenEmpty(Map<String, Object> dataMap) {
		if (dataMap.isEmpty()) {
			throw new NotFoundException(FAILED_TOKEN_NOT_FOUND_ERROR);
		}
	}
}
