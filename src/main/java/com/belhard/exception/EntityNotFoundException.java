package com.belhard.exception;

@SuppressWarnings("serial")
public class EntityNotFoundException extends MyAppException {

	public EntityNotFoundException() {
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(Throwable cause) {
		super(cause);
	}

}
