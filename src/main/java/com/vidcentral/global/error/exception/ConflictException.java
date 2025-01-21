package com.vidcentral.global.error.exception;

import com.vidcentral.global.error.model.ErrorMessage;

public class ConflictException extends VIDCentralException {

	public ConflictException(ErrorMessage errorMessage) {
		super(errorMessage);
	}
}
