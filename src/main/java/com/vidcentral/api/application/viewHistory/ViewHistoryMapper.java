package com.vidcentral.api.application.viewHistory;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.domain.viewHistory.entity.ViewHistory;
import com.vidcentral.api.dto.response.viewHistory.ViewHistoryListResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViewHistoryMapper {

	public static ViewHistory toViewHistory(Member member, Video video) {
		return ViewHistory.builder()
			.member(member)
			.video(video)
			.build();
	}

	public static ViewHistoryListResponse toViewHistoryListResponse(ViewHistory viewHistory) {
		return ViewHistoryListResponse.builder()
			.title(viewHistory.getVideo().getTitle())
			.description(viewHistory.getVideo().getDescription())
			.videoURL(viewHistory.getVideo().getVideoURL())
			.views(viewHistory.getVideo().getViews())
			.watchedAt(viewHistory.getWatchedAt())
			.build();
	}
}
