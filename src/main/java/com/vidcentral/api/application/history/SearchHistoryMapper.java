package com.vidcentral.api.application.history;

import com.vidcentral.api.domain.history.entity.SearchHistory;
import com.vidcentral.api.domain.member.entity.Member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchHistoryMapper {

	public static SearchHistory toSearchHistory(Member member, String keyword) {
		return SearchHistory.builder()
			.member(member)
			.searchKeyword(keyword)
			.build();
	}
}
