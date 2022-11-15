package com.belhard.exception;

import org.springframework.validation.Errors;

import lombok.Getter;

@SuppressWarnings("serial")
public class ValidationException extends MyAppException {
	@Getter
	private final Errors errors;

	public ValidationException(Errors errors) {
		this.errors = errors;
	}
}
