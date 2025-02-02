package com.vidcentral.api.application.comment;

import org.springframework.stereotype.Service;

import com.vidcentral.api.domain.comment.entity.Comment;
import com.vidcentral.api.domain.comment.repository.CommentRepository;
import com.vidcentral.api.dto.request.comment.UpdateCommentRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentWriteService {

	private final CommentRepository commentRepository;

	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}

	public void updateComment(Comment comment, UpdateCommentRequest updateCommentRequest) {
		comment.updateContent(updateCommentRequest.content());
	}

	public void deleteComment(Comment comment) {
		commentRepository.delete(comment);
	}
}
