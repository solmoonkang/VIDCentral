package com.vidcentral.api.application.comment;

import static com.vidcentral.global.error.model.ErrorMessage.*;

import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.comment.entity.Comment;
import com.vidcentral.api.domain.comment.repository.CommentRepository;
import com.vidcentral.api.domain.video.entity.Video;
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
}
