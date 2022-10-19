package com.belhard.exception;

public class WrongValueException extends MyAppException {

	public WrongValueException() {
	}

	public WrongValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongValueException(String message) {
		super(message);
	}

	public WrongValueException(Throwable cause) {
		super(cause);
	}

}
