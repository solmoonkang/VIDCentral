package com.vidcentral.api.dto.request.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignUpRequest(
	@Email(message = "유효한 이메일 형식을 입력해주세요.")
	String email,

	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	@Size(min = 5, max = 20, message = "비밀번호는 5자 이상 20자 이하로 설정해야 합니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,20}$",
		message = "비밀번호는 영문자와 숫자를 모두 포함해야 합니다.")
	String password,

	@NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.")
	String checkPassword,

	@NotBlank(message = "닉네임은 필수 입력 항목입니다.")
	@Size(min = 3, max = 20, message = "닉네임은 3자 이상 20자 이하로 설정해야 합니다.")
	String nickname
) {
}
