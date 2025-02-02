package com.vidcentral.api.domain.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidcentral.api.domain.comment.entity.Comment;
import com.vidcentral.api.domain.video.entity.Video;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findCommentsByVideo(Video video);
}
