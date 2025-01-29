package com.vidcentral.api.domain.video.entity;

import static java.util.Objects.*;

import java.util.ArrayList;
import java.util.List;

import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.global.common.entity.BaseTimeEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "VIDEO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "video_id")
	private Long videoId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "video_url", nullable = false)
	private String videoURL;

	@Column(name = "views", nullable = false)
	private Long views = 0L;

	@ElementCollection(targetClass = VideoTag.class)
	@CollectionTable(name = "video_tags", joinColumns = @JoinColumn(name = "video_id"))
	@Column(name = "video_tags")
	@Enumerated(EnumType.STRING)
	private List<VideoTag> videoTags = new ArrayList<>();

	@Builder
	private Video(Member member, String title, String description, String videoURL, List<VideoTag> videoTags) {
		this.member = member;
		this.title = title;
		this.description = description;
		this.videoURL = videoURL;
		this.videoTags = videoTags;
	}

	public void updateTitle(String title) {
		this.title = requireNonNullElse(title, this.title);
	}

	public void updateDescription(String description) {
		this.description = requireNonNullElse(description, this.description);
	}

	public void updateVideoURL(String newVideoURL) {
		this.videoURL = requireNonNullElse(newVideoURL, this.videoURL);
	}
}
