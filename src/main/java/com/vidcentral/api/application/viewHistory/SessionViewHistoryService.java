package com.vidcentral.api.application.viewHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.viewHistory.entity.ViewHistory;

@Service
public class SessionViewHistoryService {

	private final Map<String, List<ViewHistory>> anonymousViewHistoryMap = new ConcurrentHashMap<>();

	public void addSessionViewHistory(String anonymousId, Video video) {
		final ViewHistory viewHistory = ViewHistoryMapper.toViewHistory(null, video);
		anonymousViewHistoryMap.computeIfAbsent(anonymousId, existingAnonymousId -> new ArrayList<>()).add(viewHistory);
	}

	public List<ViewHistory> searchSessionViewHistory(String anonymousId) {
		return anonymousViewHistoryMap.getOrDefault(anonymousId, new ArrayList<>());
	}
}
