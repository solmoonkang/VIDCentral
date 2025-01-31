package com.vidcentral.api.presentation.like;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vidcentral.api.application.like.LikeMangerService;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.global.auth.annotation.AuthenticationMember;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
public class LikeController {

	private final LikeMangerService likeMangerService;

	@PostMapping("/like/{videoId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
		summary = "좋아요 API",
		description = "사용자가 비디오에 좋아요를 누릅니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공 - 좋아요, 사용자가 비디오에 좋아요를 등록했습니다."),
		@ApiResponse(responseCode = "400", description = "실패 - 잘못된 요청, 필수 입력값이 누락되었거나 형식이 올바르지 않습니다."),
		@ApiResponse(responseCode = "404", description = "실패 - 좋아요를 누른 비디오를 찾을 수 없습니다."),
		@ApiResponse(responseCode = "500", description = "실패 - 서버 오류, 요청 처리 중 문제가 발생했습니다.")
	})
	public ResponseEntity<String> likeVideo(@AuthenticationMember AuthMember authMember, @PathVariable Long videoId) {
		likeMangerService.likeVideo(authMember, videoId);
		return ResponseEntity.ok().body("성공적으로 비디오에 좋아요가 등록되었습니다.");
	}

	@PostMapping("/dislike/{videoId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
		summary = "싫어요 API",
		description = "사용자가 비디오에 싫어요를 누릅니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공 - 싫어요, 사용자가 비디오에 싫어요를 등록했습니다."),
		@ApiResponse(responseCode = "400", description = "실패 - 잘못된 요청, 필수 입력값이 누락되었거나 형식이 올바르지 않습니다."),
		@ApiResponse(responseCode = "404", description = "실패 - 싫어요를 누른 비디오를 찾을 수 없습니다."),
		@ApiResponse(responseCode = "500", description = "실패 - 서버 오류, 요청 처리 중 문제가 발생했습니다.")
	})
	public ResponseEntity<String> dislikeVideo(@AuthenticationMember AuthMember authMember,
		@PathVariable Long videoId) {
		likeMangerService.dislikeVideo(authMember, videoId);
		return ResponseEntity.ok().body("성공적으로 비디오에 싫어요가 등록되었습니다.");
	}
}
