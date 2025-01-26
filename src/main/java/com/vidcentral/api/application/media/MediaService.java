package com.vidcentral.api.application.media;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vidcentral.api.domain.image.ImageName;
import com.vidcentral.api.domain.image.ImageProcessor;
import com.vidcentral.api.domain.image.ImageProperties;
import com.vidcentral.api.domain.image.NewImage;
import com.vidcentral.api.domain.video.entity.VideoName;
import com.vidcentral.api.domain.video.entity.VideoProcessor;
import com.vidcentral.api.domain.video.entity.VideoProperties;
import com.vidcentral.api.infrastructure.s3.S3ManagerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MediaService {

	private final S3ManagerService s3ManagerService;

	@Transactional
	public List<String> uploadImages(List<? extends MultipartFile> multipartFiles, ImageProperties imageProperties) {
		return multipartFiles.stream()
			.map(multipartFile -> processImageUpload(multipartFile, imageProperties))
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteImage(String imageURL) {
		s3ManagerService.deleteFile(imageURL);
	}

	@Transactional
	public String uploadVideo(MultipartFile multipartFile, VideoProperties videoProperties) {
		final VideoName videoName = VideoName.createFromMultipartFile(multipartFile, videoProperties);
		final VideoProcessor videoProcessor = new VideoProcessor(multipartFile);

		return s3ManagerService.uploadFile(videoName.getFileName(), videoProcessor.originalVideoFile());
	}

	@Transactional
	public void deleteVideo(String videoURL) {
		s3ManagerService.deleteFile(videoURL);
	}

	private String processImageUpload(MultipartFile multipartFile, ImageProperties imageProperties) {
		final ImageName imageName = ImageName.createFromMultipartFile(multipartFile, imageProperties);
		final ImageProcessor imageProcessor = new ImageProcessor(multipartFile);
		final NewImage resizedImage = imageProcessor.resizeImageToProperties(imageProperties);

		return s3ManagerService.uploadFile(imageName.getFileName(), resizedImage);
	}
}
