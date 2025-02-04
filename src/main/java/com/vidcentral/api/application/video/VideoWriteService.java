package com.vidcentral.api.application.video;

import static com.vidcentral.global.common.util.GlobalConstant.*;

import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.video.repository.VideoManageRepository;
import com.vidcentral.api.domain.video.repository.VideoRepository;
import com.vidcentral.api.dto.request.video.UpdateVideoRequest;

import lombok.RequiredArgsConstructor;

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

	@Async("taskExecutor")
	public void incrementVideoViewsAsync(Long videoId) {
		videoManageRepository.incrementViews(videoId);
	}

	@Scheduled(fixedRate = 600000)
	public void syncVideoViewsFromRedisToMySQL() {
		final Set<String> videoKeys = videoManageRepository.findAllVideoIds();

		final List<Long> videoIds = videoKeys.stream()
			.map(key -> Long.parseLong(key.split(COLON_DELIMITER)[1]))
			.toList();

		videoIds.forEach(videoId -> {
			final Long views = videoManageRepository.findViews(videoId);
			updateVideoViews(videoId, views);
		});
	}

	private void updateVideoViews(Long videoId, Long views) {
		if (views != null) {
			videoRepository.incrementViews(videoId, views);
			videoManageRepository.deleteViews(videoId);
		}
	}
}
