package com.vidcentral.api.domain.video.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidcentral.api.domain.video.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

	List<Video> findVideosByTitle(String title);

	List<Video> findVideosByDescription(String description);
}
