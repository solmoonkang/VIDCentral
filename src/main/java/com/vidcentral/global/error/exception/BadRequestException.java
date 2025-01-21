package com.vidcentral.global.error.exception;

import com.vidcentral.global.error.model.ErrorMessage;

public class BadRequestException extends VIDCentralException {

	public BadRequestException(ErrorMessage errorMessage) {
		super(errorMessage);
	}
}
