package com.vidcentral.api.application.video;

import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.dto.request.video.UpdateVideoRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoWriteService {

	public void updateVideo(Video video, UpdateVideoRequest updateVideoRequest, String newVideoURL) {
		video.updateTitle(updateVideoRequest.title());
		video.updateDescription(updateVideoRequest.description());
		video.updateVideoURL(newVideoURL);
		video.updateVideoTags(updateVideoRequest.videoTags());
	}
}
