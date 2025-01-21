package com.vidcentral.global.error.exception;

import com.vidcentral.global.error.model.ErrorMessage;

public class NotFoundException extends VIDCentralException {

	public NotFoundException(ErrorMessage errorMessage) {
		super(errorMessage);
	}
}
