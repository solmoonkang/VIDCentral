package com.vidcentral.api.application.video;

import static com.vidcentral.global.common.util.GlobalConstant.*;
import static com.vidcentral.global.common.util.RedisConstant.*;
import static com.vidcentral.global.error.model.ErrorMessage.*;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.video.repository.VideoManageRepository;
import com.vidcentral.api.domain.video.repository.VideoRepository;
import com.vidcentral.api.dto.request.video.UpdateVideoRequest;
import com.vidcentral.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoWriteService {

	private final VideoRepository videoRepository;
	private final VideoManageRepository videoManageRepository;

	public Video saveVideo(Video video) {
		return videoRepository.save(video);
	}

	public void updateVideo(Video video, UpdateVideoRequest updateVideoRequest, String newVideoURL) {
		video.updateTitle(updateVideoRequest.title());
		video.updateDescription(updateVideoRequest.description());
		video.updateVideoURL(newVideoURL);
		video.updateVideoTags(updateVideoRequest.videoTags());
	}

	public void deleteVideo(Video video) {
		videoRepository.delete(video);
	}

	@Async
	public void incrementVideoViewsAsync(Long videoId) {
		CompletableFuture.runAsync(() -> {
			videoManageRepository.incrementViews(videoId);
		}).thenAccept(success -> {
			log.info("[✅ LOGGER] 조회 수 증가가 완료되었습니다.{}", videoId);
		}).exceptionally(exception -> {
			log.warn("[❎ LOGGER] 조회 수 증가 중 오류가 발생했습니다.{}", exception.getMessage());
			throw new NotFoundException(FAILED_VIDEO_NOT_FOUND_ERROR);
		});
	}

	@Scheduled(fixedRate = 600000)
	public void syncVideoViewsFromRedisToMySQL() {
		Set<String> videoKeys = videoManageRepository.findAllKeys(REDIS_VIDEO_VIEW_PREFIX + WILDCARD);

		for (String key : videoKeys) {
			final Long videoId = Long.parseLong(key.split(COLON_DELIMITER)[1]);
			final Long views = videoManageRepository.findViews(videoId);

			if (views != null) {
				videoRepository.incrementViews(videoId, views);
				videoManageRepository.deleteViews(videoId);
			}
		}
	}
}
