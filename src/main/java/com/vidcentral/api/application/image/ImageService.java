package com.vidcentral.api.application.image;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vidcentral.api.domain.image.ImageName;
import com.vidcentral.api.domain.image.ImageProcessor;
import com.vidcentral.api.domain.image.ImageProperties;
import com.vidcentral.api.domain.image.NewImage;
import com.vidcentral.api.infrastructure.s3.S3ManagerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

	private final S3ManagerService s3ManagerService;

	@Transactional
	public List<String> uploadImages(List<? extends MultipartFile> multipartFiles, ImageProperties imageProperties) {
		return multipartFiles.stream()
			.map(multipartFile -> processImageUpload(multipartFile, imageProperties))
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteImage(String imageURL) {
		s3ManagerService.deleteImageFile(imageURL);
	}

	private String processImageUpload(MultipartFile multipartFile, ImageProperties imageProperties) {
		final ImageName imageName = ImageName.createFromMultipartFile(multipartFile, imageProperties);
		final ImageProcessor imageProcessor = new ImageProcessor(multipartFile);
		final NewImage resizedImage = imageProcessor.resizeImageToProperties(imageProperties);

		return s3ManagerService.uploadImageFile(imageName.getFileName(), resizedImage);
	}
}
