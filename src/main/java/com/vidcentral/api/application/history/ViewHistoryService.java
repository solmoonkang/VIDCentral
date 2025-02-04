package com.vidcentral.api.application.history;

import static com.vidcentral.global.error.model.ErrorMessage.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vidcentral.api.application.member.MemberReadService;
import com.vidcentral.api.application.page.PageMapper;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.history.entity.ViewHistory;
import com.vidcentral.api.domain.history.repository.ViewHistoryRepository;
import com.vidcentral.api.dto.response.page.PageResponse;
import com.vidcentral.api.dto.response.history.ViewHistoryListResponse;
import com.vidcentral.global.error.exception.BadRequestException;

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

	public PageResponse<ViewHistoryListResponse> findAllViewHistory(AuthMember authMember, Pageable pageable) {
		return Optional.ofNullable(authMember)
			.map(loginMember -> searchAllViewHistory(loginMember, pageable))
			.orElseThrow(() -> new BadRequestException(FAILED_INVALID_REQUEST_ERROR));
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
