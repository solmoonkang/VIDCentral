package com.vidcentral.api.domain.video.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vidcentral.api.domain.video.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

	@Query("SELECT DISTINCT V FROM Video V " +
		"WHERE V.title LIKE %:keyword% OR V.description LIKE %:keyword%")
	Page<Video> findDistinctVideosByKeyword(@Param("keyword") String keyword, Pageable pageable);

	@Modifying
	@Query("UPDATE Video V SET V.views = V.views + :views WHERE V.videoId = :videoId")
	void incrementViews(@Param("videoId") Long videoId, @Param("views") Long views);
}
