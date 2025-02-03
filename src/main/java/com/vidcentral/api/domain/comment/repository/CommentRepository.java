package com.vidcentral.api.domain.comment.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidcentral.api.domain.comment.entity.Comment;
import com.vidcentral.api.domain.video.entity.Video;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	Optional<Comment> findCommentByVideo(Video video);

	Page<Comment> findCommentsByVideo(Video video, Pageable pageable);
}
