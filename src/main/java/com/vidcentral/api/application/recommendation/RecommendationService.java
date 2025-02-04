package com.vidcentral.api.application.recommendation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vidcentral.api.application.page.PageMapper;
import com.vidcentral.api.application.video.VideoMapper;
import com.vidcentral.api.application.video.VideoReadService;
import com.vidcentral.api.domain.history.entity.ViewHistory;
import com.vidcentral.api.domain.history.repository.ViewHistoryRepository;
import com.vidcentral.api.domain.like.entity.Like;
import com.vidcentral.api.domain.like.repository.LikeRepository;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.member.repository.MemberRepository;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.dto.response.page.PageResponse;
import com.vidcentral.api.dto.response.video.VideoInfoResponse;
import com.vidcentral.api.dto.response.video.VideoListResponse;
import com.vidcentral.api.dto.response.video.VideoRecommendResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

	private static final double BASE_SCORE = 1.0;
	private static final double TAG_SCORE = 0.5;
	private static final double CONTENT_SCORE = 0.5;

	private final VideoReadService videoReadService;
	private final MemberRepository memberRepository;
	private final ViewHistoryRepository viewHistoryRepository;
	private final LikeRepository likeRepository;

	public PageResponse<VideoRecommendResponse> findAllRecommendVideos(Member member, Pageable pageable) {
		final VideoInfoResponse videoInfoResponse = findMemberVideoInfo(member);
		final Set<Long> viewedVideoIds = videoInfoResponse.viewedVideoIds();
		final Set<Long> likedVideoIds = videoInfoResponse.likedVideoIds();
		final List<Member> similarMembers = findSimilarMembers(member, viewedVideoIds);

		final Map<Long, Double> videoScores = calculateVideoScores(similarMembers, viewedVideoIds, likedVideoIds);

		final List<VideoRecommendResponse> recommendResponses = videoScores.entrySet().stream()
			.map(entry -> createRecommendationResponse(entry.getKey(), entry.getValue()))
			.filter(Objects::nonNull)
			.sorted(Comparator.comparingDouble(VideoRecommendResponse::score).reversed())
			.toList();

		return PageMapper.toPageResponse(
			PageMapper.toPageImpl(recommendResponses, pageable, recommendResponses.size()));
	}

	private VideoInfoResponse findMemberVideoInfo(Member member) {
		final List<ViewHistory> viewHistories = viewHistoryRepository.findAllByMember(member);
		final List<Like> likes = likeRepository.findAllByMember(member);

		final Set<Long> viewedVideoIds = viewHistories.stream()
			.map(viewHistory -> viewHistory.getVideo().getVideoId())
			.collect(Collectors.toSet());

		final Set<Long> likedVideoIds = likes.stream()
			.map(like -> like.getVideo().getVideoId())
			.collect(Collectors.toSet());

		return VideoMapper.toVideoInfoResponse(viewedVideoIds, likedVideoIds);
	}

	private List<Member> findSimilarMembers(Member member, Set<Long> viewedVideoIds) {
		return memberRepository.findAll().stream()
			.filter(similarMember -> !similarMember.equals(member))
			.filter(similarMember -> hasCommonViewedVideos(similarMember, viewedVideoIds))
			.toList();
	}

	private Map<Long, Video> fetchAllVideos(List<Long> videoIds) {
		return videoReadService.findAllVideoIds(videoIds).stream()
			.collect(Collectors.toMap(Video::getVideoId, Function.identity()));
	}

	private Map<Member, List<ViewHistory>> fetchAllViewHistories(List<Member> similarMembers) {
		return similarMembers.stream()
			.collect(Collectors.toMap(Function.identity(), viewHistoryRepository::findAllByMember));
	}

	private Map<Long, Double> calculateVideoScores(List<Member> similarMembers, Set<Long> viewedVideoIds,
		Set<Long> likedVideoIds) {

		final Map<Long, Double> videoScores = new HashMap<>();
		final List<Long> videoIds = new ArrayList<>();

		final Map<Member, List<ViewHistory>> viewHistoriesByMember = fetchAllViewHistories(similarMembers);

		similarMembers.forEach(similarMember -> {
			final List<Video> videos = viewHistoriesByMember.get(similarMember).stream()
				.map(ViewHistory::getVideo)
				.filter(video -> !viewedVideoIds.contains(video.getVideoId()))
				.toList();
			videoIds.addAll(videos.stream().map(Video::getVideoId).toList());
		});

		final Map<Long, Video> allVideos = fetchAllVideos(videoIds);
		allVideos.values().forEach(video -> updateRecommendVideoScore(videoScores, video, likedVideoIds));

		return videoScores;
	}

	private void updateRecommendVideoScore(Map<Long, Double> videoScores, Video video, Set<Long> likedVideoIds) {
		videoScores.merge(video.getVideoId(), BASE_SCORE, Double::sum);

		if (hasCommonTags(video, likedVideoIds)) {
			videoScores.merge(video.getVideoId(), TAG_SCORE, Double::sum);
		}

		if (isSimilarContent(video, likedVideoIds)) {
			videoScores.merge(video.getVideoId(), CONTENT_SCORE, Double::sum);
		}
	}

	private boolean hasCommonTags(Video video, Set<Long> likedVideoIds) {
		return likedVideoIds.stream().anyMatch(likedVideoId -> {
			final Video likedVideo = videoReadService.findVideo(likedVideoId);
			return likedVideo.getVideoTags().stream()
				.anyMatch(tag -> video.getVideoTags().contains(tag));
		});
	}

	private boolean isSimilarContent(Video video, Set<Long> likedVideoIds) {
		return likedVideoIds.stream().anyMatch(likedVideoId -> {
			final Video likedVideo = videoReadService.findVideo(likedVideoId);
			return (video.getTitle().contains(likedVideo.getTitle()) ||
				video.getDescription().contains(likedVideo.getDescription()));
		});
	}

	private boolean hasCommonViewedVideos(Member similarMember, Set<Long> viewedVideoIds) {
		return viewHistoryRepository.findAllByMember(similarMember).stream()
			.anyMatch(viewHistory -> viewedVideoIds.contains(viewHistory.getVideo().getVideoId()));
	}

	private VideoRecommendResponse createRecommendationResponse(Long videoId, Double score) {
		final Video video = videoReadService.findVideo(videoId);
		final VideoListResponse videoListResponse = VideoMapper.toVideoListResponse(video);
		return VideoMapper.toVideoRecommendResponse(videoId, videoListResponse, score);
	}
}
