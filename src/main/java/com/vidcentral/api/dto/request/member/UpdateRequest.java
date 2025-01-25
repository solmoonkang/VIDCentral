package com.vidcentral.api.dto.request.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UpdateRequest(
	@NotBlank(message = "닉네임은 필수 입력 항목입니다.")
	@Size(min = 3, max = 20, message = "닉네임은 3자 이상 20자 이하로 설정해야 합니다.")
	String nickname,

	@Size(max = 50, message = "소개글은 최대 50자까지 입력이 가능합니다.")
	String introduce
) {
}
