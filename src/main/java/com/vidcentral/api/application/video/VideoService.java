package com.vidcentral.api.application.video;

import static com.vidcentral.api.domain.video.entity.VideoProperties.*;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vidcentral.api.application.media.MediaService;
import com.vidcentral.api.application.member.MemberReadService;
import com.vidcentral.api.application.page.PageMapper;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.dto.request.video.SearchVideoRequest;
import com.vidcentral.api.dto.request.video.UpdateVideoRequest;
import com.vidcentral.api.dto.request.video.UploadVideoRequest;
import com.vidcentral.api.dto.response.page.PageResponse;
import com.vidcentral.api.dto.response.video.VideoDetailResponse;
import com.vidcentral.api.dto.response.video.VideoListResponse;
import com.vidcentral.api.dto.response.viewHistory.ViewHistoryListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoService {

	private final MediaService mediaService;
	private final MemberReadService memberReadService;
	private final VideoWriteService videoWriteService;
	private final VideoReadService videoReadService;

	@Transactional
	public Video uploadVideo(AuthMember authMember, UploadVideoRequest uploadVideoRequest, MultipartFile newVideoURL) {
		final String videoURL = mediaService.uploadVideo(newVideoURL, DEFAULT_VIDEO);
		final Member loginMember = memberReadService.findMember(authMember.email());

		videoReadService.validateTagCount(uploadVideoRequest.videoTags());

		final Video video = VideoMapper.toVideo(loginMember, uploadVideoRequest, videoURL);
		return videoWriteService.saveVideo(video);
	}

	public PageResponse<VideoListResponse> searchAllVideos(int page, int size) {
		final Page<Video> videoPage = videoReadService.findAllVideos(PageRequest.of(page, size));
		return PageMapper.toPageResponse(videoPage.map(VideoMapper::toVideoListResponse));
	}

	public PageResponse<VideoListResponse> searchAllVideosByKeyword(SearchVideoRequest searchVideoRequest, int page, int size) {
		final Set<Video> distinctVideos = videoReadService
			.findAllVideosByKeyword(searchVideoRequest.keyword(), PageRequest.of(page, size));

		final List<VideoListResponse> videoListResponses = distinctVideos.stream()
			.map(VideoMapper::toVideoListResponse)
			.toList();

		final Page<VideoListResponse> videoListResponsePage = PageMapper
			.toPageImpl(videoListResponses, PageRequest.of(page, size), distinctVideos.size());
		return PageMapper.toPageResponse(videoListResponsePage);
	}

	public VideoDetailResponse searchVideo(AuthMember authMember, Long videoId) {
		final Video video = videoReadService.findVideo(videoId);
		videoReadService.saveViewHistory(authMember, video);
		videoWriteService.incrementVideoViews(video);
		return VideoMapper.toVideoDetailsResponse(video);
	}

	public List<ViewHistoryListResponse> searchAllViewHistory(AuthMember authMember) {
		return videoReadService.findAllViewHistory(authMember);
	}

	public List<VideoListResponse> searchAllRecommendationVideos(AuthMember authMember) {
		final Member loginMember = memberReadService.findMember(authMember.email());
		return videoReadService.findAllRecommendationVideos(loginMember);
	}

	@Transactional
	public void updateVideo(AuthMember authMember, Long videoId, UpdateVideoRequest updateVideoRequest, MultipartFile videoURL) {
		final Member loginMember = memberReadService.findMember(authMember.email());
		final Video video = videoReadService.findVideo(videoId);

		videoReadService.validateMemberHasAccess(video.getMember().getEmail(), loginMember.getEmail());
		videoReadService.validateTagCount(updateVideoRequest.videoTags());

		final String newVideoURL = mediaService.uploadVideo(videoURL, DEFAULT_VIDEO);
		videoWriteService.updateVideo(video, updateVideoRequest, newVideoURL);
	}

	@Transactional
	public void deleteVideo(AuthMember authMember, Long videoId) {
		final Member loginMember = memberReadService.findMember(authMember.email());
		final Video video = videoReadService.findVideo(videoId);
		videoReadService.validateMemberHasAccess(video.getMember().getEmail(), loginMember.getEmail());

		mediaService.deleteVideo(video.getVideoURL());
		videoWriteService.deleteVideo(video);
	}
}
