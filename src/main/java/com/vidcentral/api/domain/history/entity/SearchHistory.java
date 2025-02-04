package com.vidcentral.api.domain.history.entity;

import java.time.LocalDateTime;

import com.vidcentral.api.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "SEARCH_HISTORIES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "search_history_id")
	private Long searchHistoryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(name = "search_keyword", nullable = false)
	private String searchKeyword;

	@Column(name = "search_at", nullable = false)
	private LocalDateTime searchAt;

	@Builder
	private SearchHistory(Member member, String searchKeyword) {
		this.member = member;
		this.searchKeyword = searchKeyword;
		this.searchAt = LocalDateTime.now();
	}
}
