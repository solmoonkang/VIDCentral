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
public class S3ManageService {

	private final S3Client s3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	public String uploadFile(String key, MultipartFile multipartFile) {
		try {
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.bucket(bucketName)
				.key(key)
				.contentType(multipartFile.getContentType())
				.build();

			s3Client.putObject(putObjectRequest,
				RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));

			return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, "ap-northeast-2", key);
		} catch (IOException e) {
			throw new RuntimeException("파일 업로드 실패", e);
		}
	}

	public void deleteFile(String objectURL) {
		String key = objectURL.replace("https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/", "");

		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
			.bucket(bucketName)
			.key(key)
			.build();

		s3Client.deleteObject(deleteObjectRequest);
	}
}
