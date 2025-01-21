package com.vidcentral.global.error.exception;

import com.vidcentral.global.error.model.ErrorMessage;

public class VIDCentralException extends RuntimeException {

	public VIDCentralException(ErrorMessage errorMessage) {
		super(errorMessage.getMessage());
	}
}
