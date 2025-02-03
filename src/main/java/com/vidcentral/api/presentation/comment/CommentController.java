package com.vidcentral.api.presentation.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vidcentral.api.application.comment.CommentService;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.dto.request.comment.UpdateCommentRequest;
import com.vidcentral.api.dto.request.comment.UploadCommentRequest;
import com.vidcentral.api.dto.response.comment.CommentListResponse;
import com.vidcentral.api.dto.response.page.PageResponse;
import com.vidcentral.global.auth.annotation.AuthenticationMember;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/upload/{videoId}")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(
		summary = "댓글 업로드 API",
		description = "사용자가 댓글을 업로드합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공 - 댓글 업로드, 댓글 정보가 생성되었습니다."),
		@ApiResponse(responseCode = "400", description = "실패 - 잘못된 요청, 필수 입력값이 누락되었거나 형식이 올바르지 않습니다."),
		@ApiResponse(responseCode = "404", description = "실패 - 해당 비디오를 찾을 수 없습니다."),
		@ApiResponse(responseCode = "500", description = "실패 - 서버 오류, 요청 처리 중 문제가 발생했습니다.")
	})
	public ResponseEntity<String> uploadComment(
		@AuthenticationMember AuthMember authMember,
		@PathVariable Long videoId,
		@Valid @RequestPart(required = false) UploadCommentRequest uploadCommentRequest) {

		commentService.uploadComment(authMember, videoId, uploadCommentRequest);
		return ResponseEntity.ok().body("성공적으로 댓글 정보를 업로드했습니다.");
	}

	@GetMapping("/{videoId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
		summary = "모든 댓글 조회 API",
		description = "모든 댓글을 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공 - 모든 댓글 조회, 모든 댓글 정보를 조회했습니다."),
		@ApiResponse(responseCode = "404", description = "실패 - 해당 회원을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "500", description = "실패 - 서버 오류, 요청 처리 중 문제가 발생했습니다.")
	})
	public ResponseEntity<PageResponse<CommentListResponse>> searchAllComments(
		@PathVariable Long videoId,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {

		return ResponseEntity.ok().body(commentService.searchAllComments(videoId, page, size));
	}

	@PutMapping("/update/{videoId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
		summary = "댓글 수정 API",
		description = "사용자가 댓글 내용을 업데이트합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공 - 댓글 수정, 댓글 정보가 업데이트되었습니다."),
		@ApiResponse(responseCode = "400", description = "실패 - 잘못된 요청, 필수 입력값이 누락되었거나 형식이 올바르지 않습니다."),
		@ApiResponse(responseCode = "404", description = "실패 - 해당 비디오를 찾을 수 없습니다."),
		@ApiResponse(responseCode = "409", description = "실패 - 유효하지 않은 댓글입니다."),
		@ApiResponse(responseCode = "500", description = "실패 - 서버 오류, 요청 처리 중 문제가 발생했습니다.")
	})
	public ResponseEntity<String> updateComment(
		@AuthenticationMember AuthMember authMember,
		@PathVariable Long videoId,
		@Valid @RequestPart(required = false) UpdateCommentRequest updateCommentRequest) {

		commentService.updateComment(authMember, videoId, updateCommentRequest);
		return ResponseEntity.ok().body("성공적으로 댓글 정보를 업데이트했습니다.");
	}

	@DeleteMapping("/delete/{videoId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
		summary = "댓글 삭제 API",
		description = "사용자가 댓글을 삭제합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공 - 댓글 삭제, 댓글 정보가 삭제되었습니다."),
		@ApiResponse(responseCode = "400", description = "실패 - 잘못된 요청, 필수 입력값이 누락되었거나 형식이 올바르지 않습니다."),
		@ApiResponse(responseCode = "404", description = "실패 - 해당 비디오를 찾을 수 없습니다."),
		@ApiResponse(responseCode = "409", description = "실패 - 유효하지 않은 댓글입니다."),
		@ApiResponse(responseCode = "500", description = "실패 - 서버 오류, 요청 처리 중 문제가 발생했습니다.")
	})
	public ResponseEntity<String> deleteComment(
		@AuthenticationMember AuthMember authMember,
		@PathVariable Long videoId) {

		commentService.deleteComment(authMember, videoId);
		return ResponseEntity.ok().body("성공적으로 댓글 정보를 삭제했습니다.");
	}
}
