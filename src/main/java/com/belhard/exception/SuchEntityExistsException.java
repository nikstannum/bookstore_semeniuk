package com.belhard.exception;

@SuppressWarnings("serial")
public class SuchEntityExistsException extends MyAppException {

	public SuchEntityExistsException() {
	}

	public SuchEntityExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public SuchEntityExistsException(String message) {
		super(message);
	}

	public SuchEntityExistsException(Throwable cause) {
		super(cause);
	}

}
