package com.vidcentral.api.infrastructure.s3;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3ManagerService {

	private final S3Client s3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Value("${cloud.aws.cloudfront.domain}")
	private String cloudFrontURL;

	public String uploadImageFile(String key, MultipartFile multipartFile) {
		try {
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.bucket(bucketName)
				.key(key)
				.contentType(multipartFile.getContentType())
				.build();

			s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));

			return cloudFrontURL + key;
		} catch (IOException e) {
			throw new RuntimeException("파일 업로드 실패", e);
		}
	}

	public void deleteImageFile(String objectURL) {
		String key = objectURL.replace(cloudFrontURL, "");

		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
			.bucket(bucketName)
			.key(key)
			.build();

		s3Client.deleteObject(deleteObjectRequest);
	}
}
