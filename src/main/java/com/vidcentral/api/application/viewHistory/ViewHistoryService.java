package com.vidcentral.api.application.viewHistory;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vidcentral.api.application.member.MemberReadService;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.viewHistory.entity.ViewHistory;
import com.vidcentral.api.domain.viewHistory.repository.ViewHistoryRepository;
import com.vidcentral.api.dto.response.viewHistory.ViewHistoryListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewHistoryService {

	private final MemberReadService memberReadService;
	private final SessionViewHistoryService sessionViewHistoryService;
	private final ViewHistoryRepository viewHistoryRepository;

	public void saveViewHistory(Member member, Video video) {
		final ViewHistory viewHistory = ViewHistoryMapper.toViewHistory(member, video);
		viewHistoryRepository.save(viewHistory);
	}

	public List<ViewHistoryListResponse> searchAllViewHistoryForLoggedInMember(AuthMember authMember) {
		final Member loginMember = memberReadService.findMember(authMember.email());
		final List<ViewHistory> viewHistoryList = viewHistoryRepository.findViewHistoriesByMember(loginMember);

		return viewHistoryList.stream()
			.map(ViewHistoryMapper::toViewHistoryListResponse)
			.toList();
	}

	public List<ViewHistoryListResponse> searchAllViewHistoryForAnonymousMember(String anonymousId) {
		return sessionViewHistoryService.searchSessionViewHistory(anonymousId).stream()
			.map(ViewHistoryMapper::toViewHistoryListResponse)
			.toList();
	}
}
