package com.belhard.exception;

public class MyAppException extends RuntimeException {

	public MyAppException() {
	}

	public MyAppException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyAppException(String message) {
		super(message);
	}

	public MyAppException(Throwable cause) {
		super(cause);
	}

}
