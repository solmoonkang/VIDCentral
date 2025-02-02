package com.vidcentral.api.domain.member.entity;

import static com.vidcentral.global.common.util.GlobalConstant.*;
import static com.vidcentral.global.common.util.ImageURL.*;
import static java.util.Objects.*;

import com.vidcentral.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "MEMBERS",
	indexes = {
		@Index(name = "IDX_MEMBER_EMAIL", columnList = "email", unique = true),
		@Index(name = "IDX_MEMBER_NICKNAME", columnList = "nickname", unique = true)
	})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "nickname", nullable = false, unique = true)
	private String nickname;

	@Column(name = "introduce", length = 50)
	private String introduce;

	@Column(name = "profile_image_url", nullable = false)
	private String profileImageURL;

	@Builder
	private Member(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.introduce = INTRODUCE_ME;
		this.profileImageURL = IMAGE_DOMAIN + MEMBER_PROFILE_URL;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateIntroduce(String introduce) {
		this.introduce = requireNonNullElse(introduce, this.introduce);
	}

	public void updateProfileImageURL(String newProfileImageURL) {
		this.profileImageURL = requireNonNullElse(newProfileImageURL, this.profileImageURL);
	}
}
