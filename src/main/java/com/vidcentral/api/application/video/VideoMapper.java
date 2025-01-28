package com.vidcentral.api.application.video;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.dto.request.video.UpdateVideoRequest;
import com.vidcentral.api.dto.request.video.UploadVideoRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoMapper {

	public static Video toVideo(Member member, UploadVideoRequest uploadVideoRequest, String videoURL) {
		return Video.builder()
			.member(member)
			.title(uploadVideoRequest.title())
			.description(uploadVideoRequest.description())
			.videoURL(videoURL)
			.build();
	}

	public static Video toVideo(Member member, UpdateVideoRequest updateVideoRequest, String videoURL) {
		return Video.builder()
			.member(member)
			.title(updateVideoRequest.title())
			.description(updateVideoRequest.description())
			.videoURL(videoURL)
			.build();
	}
}
