package com.vidcentral.api.application.media;

import static com.vidcentral.api.domain.image.ImageProperties.*;
import static com.vidcentral.global.common.util.MediaConstant.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vidcentral.api.domain.image.ImageName;
import com.vidcentral.api.domain.image.ImageProcessor;
import com.vidcentral.api.domain.image.NewImage;
import com.vidcentral.api.domain.video.entity.VideoName;
import com.vidcentral.api.domain.video.entity.VideoProcessor;
import com.vidcentral.api.infrastructure.s3.S3ManageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MediaService {

	private final S3ManageService s3ManageService;

	@Transactional
	public List<String> uploadImages(List<? extends MultipartFile> multipartFiles) {
		return multipartFiles.stream()
			.map(this::processImageUpload)
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteImage(String imageURL) {
		s3ManageService.deleteFile(imageURL);
	}

	@Transactional
	public String uploadVideo(MultipartFile multipartFile) {
		final VideoProcessor videoProcessor = new VideoProcessor(multipartFile);
		final VideoName videoName = VideoName.createFromMultipartFile(multipartFile);

		return s3ManageService.uploadFile(VIDEO_PATH + videoName.getFileName(), videoProcessor.originalVideoFile());
	}

	@Transactional
	public void deleteVideo(String videoURL) {
		s3ManageService.deleteFile(videoURL);
	}

	private String processImageUpload(MultipartFile multipartFile) {
		final ImageName imageName = ImageName.createFromMultipartFile(multipartFile);
		final ImageProcessor imageProcessor = new ImageProcessor(multipartFile);
		final NewImage resizedImage = imageProcessor.resizeImageToProperties(PROFILE_IMAGE);

		return s3ManageService.uploadFile(PROFILE_IMAGE_PATH + imageName.getFileName(), resizedImage);
	}
}
