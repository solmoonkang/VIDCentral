package com.vidcentral.api.application.history;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vidcentral.api.application.member.MemberReadService;
import com.vidcentral.api.application.page.PageMapper;
import com.vidcentral.api.application.video.VideoMapper;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.history.entity.SearchHistory;
import com.vidcentral.api.domain.history.repository.HistoryManageRepository;
import com.vidcentral.api.domain.history.repository.SearchHistoryRepository;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.video.repository.VideoRepository;
import com.vidcentral.api.dto.request.video.SearchVideoRequest;
import com.vidcentral.api.dto.response.page.PageResponse;
import com.vidcentral.api.dto.response.video.VideoListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

	private final MemberReadService memberReadService;
	private final VideoRepository videoRepository;
	private final HistoryManageRepository historyManageRepository;
	private final SearchHistoryRepository searchHistoryRepository;

	private SearchHistory saveSearchHistory(String email, String keyword) {
		final Member loginMember = memberReadService.findMember(email);
		return SearchHistoryMapper.toSearchHistory(loginMember, keyword);
	}

	public PageResponse<VideoListResponse> searchAllVideosByKeyword(AuthMember authMember,
		SearchVideoRequest searchVideoRequest, Pageable pageable) {

		final Member loginMember = memberReadService.findMember(authMember.email());
		historyManageRepository.saveSearchHistory(loginMember.getEmail(), searchVideoRequest.keyword());
		return findAllVideosByKeyword(searchVideoRequest, pageable);
	}

	public PageResponse<VideoListResponse> findAllVideosByKeyword(SearchVideoRequest searchVideoRequest,
		Pageable pageable) {

		final Page<Video> distinctVideos = videoRepository.findDistinctVideosByKeyword(searchVideoRequest.keyword(),
			pageable);

		final List<VideoListResponse> videoListResponses = distinctVideos.getContent().stream()
			.map(VideoMapper::toVideoListResponse)
			.toList();

		return PageMapper.toPageResponse(
			PageMapper.toPageImpl(videoListResponses, pageable, distinctVideos.getTotalElements()));
	}

	@Scheduled(fixedRate = 86400000)
	public void searchHistoriesFromRedisToMySQL() {
		final Set<String> memberEmails = historyManageRepository.findAllMemberEmails();

		memberEmails.forEach(memberEmail -> {
			final List<String> searchHistoryKeywords = historyManageRepository.findSearchHistories(memberEmail);

			final List<SearchHistory> searchHistories = searchHistoryKeywords.stream()
				.map(keyword -> saveSearchHistory(memberEmail, keyword))
				.toList();

			searchHistoryRepository.saveAll(searchHistories);
			historyManageRepository.deleteSearchHistory(memberEmail);
		});
	}
}
