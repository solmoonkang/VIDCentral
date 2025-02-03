package com.vidcentral.api.application.viewHistory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vidcentral.api.application.member.MemberReadService;
import com.vidcentral.api.application.page.PageMapper;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.viewHistory.entity.ViewHistory;
import com.vidcentral.api.domain.viewHistory.repository.ViewHistoryRepository;
import com.vidcentral.api.dto.response.page.PageResponse;
import com.vidcentral.api.dto.response.viewHistory.ViewHistoryListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewHistoryService {

	private final MemberReadService memberReadService;
	private final ViewHistoryRepository viewHistoryRepository;

	public void saveViewHistory(Member member, Video video) {
		final ViewHistory viewHistory = ViewHistoryMapper.toViewHistory(member, video);
		viewHistoryRepository.save(viewHistory);
	}

	public PageResponse<ViewHistoryListResponse> searchAllViewHistory(AuthMember authMember, Pageable pageable) {
		final Member loginMember = memberReadService.findMember(authMember.email());
		final Page<ViewHistory> viewHistoryList = viewHistoryRepository
			.findViewHistoriesByMember(loginMember, pageable);

		final List<ViewHistoryListResponse> viewHistoryListResponses = viewHistoryList.getContent().stream()
			.map(ViewHistoryMapper::toViewHistoryListResponse)
			.toList();

		return PageMapper.toPageResponse(
			PageMapper.toPageImpl(viewHistoryListResponses, pageable, viewHistoryList.getTotalElements()));
	}
}
