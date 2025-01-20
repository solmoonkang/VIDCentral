package com.vidcentral.api.presentation.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vidcentral.api.application.member.MemberService;
import com.vidcentral.api.dto.request.SignUpRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "사용자 APIs", description = "사용자 회원가입, 로그인, 로그아웃, 회원정보 조회 및 수정 기능을 제공하는 API입니다.")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(
		summary = "회원가입 API",
		description = "사용자가 이메일, 비밀번호 및 닉네임을 입력하여 새로운 계정을 생성합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "성공 - 회원가입, 사용자 정보가 저장되었습니다."),
		@ApiResponse(responseCode = "400", description = "실패 - 잘못된 요청, 필수 입력값이 누락되었거나 형식이 올바르지 않습니다."),
		@ApiResponse(responseCode = "409", description = "실패 - 중복된 이메일 혹은 닉네임, 이미 사용 중인 이메일 혹은 닉네임입니다."),
		@ApiResponse(responseCode = "500", description = "실패 - 서버 오류, 요청 처리 중 문제가 발생했습니다.")
	})
	public ResponseEntity<String> signUpMember(@RequestBody @Valid SignUpRequest signUpRequest) {
		memberService.signUpMember(signUpRequest);
		return ResponseEntity.ok().body("");
	}
}
