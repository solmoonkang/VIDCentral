package com.vidcentral.api.domain.image;

import static com.vidcentral.global.common.util.GlobalConstant.*;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageName {

	private static final String PROFILE_IMAGE = "members/profile" + DELIMITER;

	private final String fileName;

	public static ImageName createFromMultipartFile(MultipartFile multipartFile, ImageProperties imageProperties) {
		return switch (imageProperties) {
			case PROFILE_IMAGE ->
				new ImageName(PROFILE_IMAGE + multipartFile.getName() + "_" + UUID.randomUUID() + IMAGE_EXTENSION);
		};
	}
}
