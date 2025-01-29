package com.vidcentral.api.application.viewHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.video.entity.Video;

@Service
public class SessionViewHistoryService {

	private final Map<String, List<Video>> anonymousViewHistoryMap = new ConcurrentHashMap<>();

	public void addSessionViewHistory(String anonymousId, Video video) {
		anonymousViewHistoryMap.computeIfAbsent(anonymousId, viewHistory -> new ArrayList<>()).add(video);
	}

	public List<Video> getSessionViewHistory(String anonymousId) {
		return anonymousViewHistoryMap.getOrDefault(anonymousId, new ArrayList<>());
	}
}
