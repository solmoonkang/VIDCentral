package com.vidcentral.api.presentation.video;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vidcentral.api.application.video.VideoService;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.dto.request.video.UpdateVideoRequest;
import com.vidcentral.api.dto.request.video.UploadVideoRequest;
import com.vidcentral.global.auth.annotation.AuthenticationMember;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {

	private final VideoService videoService;

	@PostMapping("/upload")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(
		summary = "비디오 업로드 API",
		description = "사용자가 비디오 제목, 설명, 파일을 업로드합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공 - 비디오 업로드, 비디오 정보가 생성되었습니다."),
		@ApiResponse(responseCode = "400", description = "실패 - 잘못된 요청, 필수 입력값이 누락되었거나 형식이 올바르지 않습니다."),
		@ApiResponse(responseCode = "404", description = "실패 - 해당 회원을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "409", description = "실패 - 유효하지 않은 비디오 파일입니다."),
		@ApiResponse(responseCode = "500", description = "실패 - 서버 오류, 요청 처리 중 문제가 발생했습니다.")
	})
	public ResponseEntity<Video> uploadVideo(
		@AuthenticationMember AuthMember authMember,
		@Valid @RequestPart(required = false) UploadVideoRequest uploadVideoRequest,
		@RequestPart(name = "videoURL") MultipartFile newVideoURL) {

		Video uploadVideo = videoService.uploadVideo(authMember, uploadVideoRequest, newVideoURL);
		return ResponseEntity.created(URI.create(uploadVideo.getVideoURL())).body(uploadVideo);
	}

	@PutMapping("/update")
	@ResponseStatus(HttpStatus.OK)
	@Operation(
		summary = "비디오 수정 API",
		description = "사용자가 비디오 제목, 설명, 파일을 업데이트합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공 - 비디오 수정, 비디오 정보가 업데이트되었습니다."),
		@ApiResponse(responseCode = "400", description = "실패 - 잘못된 요청, 필수 입력값이 누락되었거나 형식이 올바르지 않습니다."),
		@ApiResponse(responseCode = "404", description = "실패 - 해당 회원을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "409", description = "실패 - 유효하지 않은 비디오 파일입니다."),
		@ApiResponse(responseCode = "500", description = "실패 - 서버 오류, 요청 처리 중 문제가 발생했습니다.")
	})
	public ResponseEntity<String> updateVideo(
		@AuthenticationMember AuthMember authMember,
		@Valid @RequestPart(required = false) UpdateVideoRequest updateVideoRequest,
		@RequestPart(name = "videoURL") MultipartFile newVideoURL) {

		videoService.updateVideo(authMember, updateVideoRequest, newVideoURL);
		return ResponseEntity.ok().body("성공적으로 비디오 정보를 업데이트했습니다.");
	}
}
