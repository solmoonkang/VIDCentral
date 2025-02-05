package com.vidcentral.api.domain.image;

import static com.vidcentral.global.common.util.MediaConstant.*;
import static com.vidcentral.global.error.model.ErrorMessage.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.vidcentral.global.error.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record ImageProcessor(
	MultipartFile originalImageFile
) {
	public ImageProcessor(MultipartFile originalImageFile) {
		this.originalImageFile = validateImage(originalImageFile);
	}

	public NewImage resizeImageToProperties(ImageProperties imageProperties) {
		BufferedImage bufferedImage = getBufferedImage();
		int width = imageProperties.getWidth();
		int height = getResizedHeight(width, bufferedImage);

		BufferedImage scaledImage = resizeImage(bufferedImage, width, height);
		byte[] bytes = convertBufferedImageToByteArray(scaledImage);

		return NewImage.of(originalImageFile.getOriginalFilename(), originalImageFile.getContentType(), bytes);
	}

	private MultipartFile validateImage(MultipartFile imageFile) {
		if (imageFile.isEmpty() || imageFile.getSize() > MAX_IMAGE_SIZE ||
			!ImageProperties.isSupportedContentType(imageFile.getContentType())) {

			throw new BadRequestException(FAILED_S3_RESIZE_ERROR);
		}

		return imageFile;
	}

	private int getResizedHeight(int width, BufferedImage bufferedImage) {
		double ratio = (double)width / bufferedImage.getWidth();
		return (int)(bufferedImage.getHeight() * ratio);
	}

	private BufferedImage resizeImage(BufferedImage bufferedImage, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics graphics = scaledImage.getGraphics();
		graphics.drawImage(bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
		graphics.dispose();

		return scaledImage;
	}

	private BufferedImage getBufferedImage() {
		try {
			return ImageIO.read(originalImageFile.getInputStream());
		} catch (IOException e) {
			log.error("[✅ LOGGER] 이미지 리사이징 에러", e);
			throw new BadRequestException(FAILED_S3_RESIZE_ERROR);
		}
	}

	private byte[] convertBufferedImageToByteArray(BufferedImage image) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			ImageIO.write(image, "png", outputStream);
			return outputStream.toByteArray();
		} catch (IOException e) {
			log.error("[✅ LOGGER] 이미지 리사이징 에러", e);
			throw new BadRequestException(FAILED_S3_RESIZE_ERROR);
		}
	}
}
