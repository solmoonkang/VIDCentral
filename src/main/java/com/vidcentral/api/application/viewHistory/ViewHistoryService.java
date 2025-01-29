package com.vidcentral.api.application.viewHistory;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.viewHistory.entity.ViewHistory;
import com.vidcentral.api.domain.viewHistory.repository.ViewHistoryRepository;
import com.vidcentral.api.dto.response.viewHistory.ViewHistoryListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewHistoryService {

	private final ViewHistoryRepository viewHistoryRepository;

	public void saveViewHistory(Member member, Video video) {
		final ViewHistory viewHistory = ViewHistoryMapper.toViewHistory(member, video);
		viewHistoryRepository.save(viewHistory);
	}

	public List<ViewHistoryListResponse> searchAllViewHistories() {
		List<ViewHistory> viewHistoryList = viewHistoryRepository.findAll();

		return viewHistoryList.stream()
			.map(ViewHistoryMapper::toViewHistoryListResponse)
			.toList();
	}
}
