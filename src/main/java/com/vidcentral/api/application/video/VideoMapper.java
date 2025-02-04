package com.vidcentral.api.application.video;

import java.util.Set;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.dto.request.video.UploadVideoRequest;
import com.vidcentral.api.dto.response.video.VideoDetailResponse;
import com.vidcentral.api.dto.response.video.VideoInfoResponse;
import com.vidcentral.api.dto.response.video.VideoListResponse;
import com.vidcentral.api.dto.response.video.VideoRecommendResponse;

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
			.videoTags(uploadVideoRequest.videoTags())
			.build();
	}

	public static VideoListResponse toVideoListResponse(Video video) {
		return VideoListResponse.builder()
			.nickname(video.getMember().getNickname())
			.title(video.getTitle())
			.videoURL(video.getVideoURL())
			.views(video.getViews())
			.build();
	}

	public static VideoDetailResponse toVideoDetailsResponse(Video video) {
		return VideoDetailResponse.builder()
			.nickname(video.getMember().getNickname())
			.title(video.getTitle())
			.description(video.getDescription())
			.videoURL(video.getVideoURL())
			.views(video.getViews())
			.build();
	}

	public static VideoRecommendResponse toVideoRecommendResponse(Long videoId, VideoListResponse videoListResponse,
		Double score) {

		return VideoRecommendResponse.builder()
			.videoId(videoId)
			.videoListResponse(videoListResponse)
			.score(score)
			.build();
	}

	public static VideoInfoResponse toVideoInfoResponse(Set<Long> viewedVideoIds, Set<Long> likedVideoIds) {
		return VideoInfoResponse.builder()
			.viewedVideoIds(viewedVideoIds)
			.likedVideoIds(likedVideoIds)
			.build();
	}
}
