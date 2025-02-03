package com.vidcentral.api.application.comment;

import static com.vidcentral.global.error.model.ErrorMessage.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vidcentral.api.application.page.PageMapper;
import com.vidcentral.api.domain.comment.entity.Comment;
import com.vidcentral.api.domain.comment.repository.CommentRepository;
import com.vidcentral.api.domain.video.entity.Video;
import com.vidcentral.api.dto.response.comment.CommentResponse;
import com.vidcentral.api.dto.response.page.PageResponse;
import com.vidcentral.global.error.exception.BadRequestException;
import com.vidcentral.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentReadService {

	private final CommentRepository commentRepository;

	public Comment findComment(Video video) {
		return commentRepository.findCommentByVideo(video)
			.orElseThrow(() -> new NotFoundException(FAILED_VIDEO_NOT_FOUND_ERROR));
	}

	public PageResponse<CommentResponse> findAllCommentsByVideo(Video video, Pageable pageable) {
		final Page<Comment> comments = commentRepository.findCommentsByVideo(video, pageable);

		final List<CommentResponse> commentResponses = comments.getContent().stream()
			.map(CommentMapper::toCommentResponse)
			.toList();

		return PageMapper.toPageResponse(
			PageMapper.toPageImpl(commentResponses, pageable, comments.getTotalElements()));
	}

	public void validateMemberHasAccess(String commentOwnerEmail, String authMemberEmail) {
		if (!commentOwnerEmail.equals(authMemberEmail)) {
			throw new BadRequestException(FAILED_INVALID_COMMENT_ACCESS_ERROR);
		}
	}
}
