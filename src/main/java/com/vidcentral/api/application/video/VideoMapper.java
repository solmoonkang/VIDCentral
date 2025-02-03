package com.vidcentral.api.application.video;

import org.springframework.data.domain.Page;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.dto.request.video.UploadVideoRequest;
import com.vidcentral.api.dto.response.page.PageResponse;
import com.vidcentral.api.dto.response.video.VideoDetailResponse;
import com.vidcentral.api.dto.response.video.VideoListResponse;

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

	public static PageResponse<VideoListResponse> toPageResponse(Page<Video> page) {
		return PageResponse.<VideoListResponse>builder()
			.content(page.map(VideoMapper::toVideoListResponse).getContent())
			.pageIndex(page.getNumber())
			.totalPages(page.getTotalPages())
			.totalElements(page.getTotalElements())
			.isLast(page.isLast())
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

	public static VideoListResponse toVideoListResponse(Video video) {
		return VideoListResponse.builder()
			.nickname(video.getMember().getNickname())
			.title(video.getTitle())
			.videoURL(video.getVideoURL())
			.views(video.getViews())
			.build();
	}
}
