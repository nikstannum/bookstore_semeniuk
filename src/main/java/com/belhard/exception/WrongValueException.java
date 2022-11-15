package com.belhard.exception;

@SuppressWarnings("serial")
public class WrongValueException extends ClientException {

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
