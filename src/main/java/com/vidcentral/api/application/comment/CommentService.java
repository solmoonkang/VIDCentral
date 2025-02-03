package com.vidcentral.api.application.comment;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vidcentral.api.application.member.MemberReadService;
import com.vidcentral.api.application.video.VideoReadService;
import com.vidcentral.api.domain.auth.entity.AuthMember;
import com.vidcentral.api.domain.comment.entity.Comment;
import com.vidcentral.api.domain.member.entity.Member;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.dto.request.comment.UpdateCommentRequest;
import com.vidcentral.api.dto.request.comment.UploadCommentRequest;
import com.vidcentral.api.dto.response.comment.CommentResponse;
import com.vidcentral.api.dto.response.page.PageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private final MemberReadService memberReadService;
	private final VideoReadService videoReadService;
	private final CommentReadService commentReadService;
	private final CommentWriteService commentWriteService;

	@Transactional
	public void uploadComment(AuthMember authMember, Long videoId, UploadCommentRequest uploadCommentRequest) {
		final Member loginMember = memberReadService.findMember(authMember.email());
		final Video video = videoReadService.findVideo(videoId);

		final Comment comment = CommentMapper.toComment(loginMember, video, uploadCommentRequest.content());
		commentWriteService.saveComment(comment);
	}

	public PageResponse<CommentResponse> searchAllComments(Long videoId, int page, int size) {
		final Video video = videoReadService.findVideo(videoId);
		return commentReadService.findAllCommentsByVideo(video, PageRequest.of(page, size));
	}

	@Transactional
	public void updateComment(AuthMember authMember, Long videoId, UpdateCommentRequest updateCommentRequest) {
		final Member loginMember = memberReadService.findMember(authMember.email());
		final Video video = videoReadService.findVideo(videoId);
		final Comment comment = commentReadService.findComment(video);

		commentReadService.validateMemberHasAccess(comment.getMember().getEmail(), loginMember.getEmail());
		commentWriteService.updateComment(comment, updateCommentRequest);
	}

	@Transactional
	public void deleteComment(AuthMember authMember, Long videoId) {
		final Member loginMember = memberReadService.findMember(authMember.email());
		final Video video = videoReadService.findVideo(videoId);
		final Comment comment = commentReadService.findComment(video);

		commentReadService.validateMemberHasAccess(comment.getMember().getEmail(), loginMember.getEmail());
		commentWriteService.deleteComment(comment);
	}
}
