package com.vidcentral.api.domain.viewHistory.entity;

import java.time.LocalDateTime;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;

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
@Table(name = "VIEW_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long viewHistoryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "video_id", nullable = false)
	private Video video;

	@Column(name = "watched_at")
	private LocalDateTime watchedAt;

	@Builder
	private ViewHistory(Member member, Video video) {
		this.member = member;
		this.video = video;
		this.watchedAt = LocalDateTime.now();
	}
}
