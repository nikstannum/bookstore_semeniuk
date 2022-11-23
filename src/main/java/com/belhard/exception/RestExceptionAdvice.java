package com.belhard.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.belhard.service.dto.error.ErrorDto;
import com.belhard.service.dto.error.ValidationResultDto;

@RestControllerAdvice("com.belhard.controller.command.rest")
public class RestExceptionAdvice {
	private static final String SERVER_ERROR = "Server error";
	private static final String CLIENT_ERROR = "Client error";
	private static final String DEFAULT_MESSAGE = "Unknown error";

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ValidationResultDto validationError(ValidationException e) {
		Map<String, List<String>> errors = mapErrors(e.getErrors());
		return new ValidationResultDto(errors);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDto error(ClientException e) {
		return new ErrorDto(CLIENT_ERROR, e.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorDto error(RuntimeException e) {
		return new ErrorDto(SERVER_ERROR, DEFAULT_MESSAGE);
	}

	private Map<String, List<String>> mapErrors(Errors rawErrors) {
		List<FieldError> listFieldsError = rawErrors.getFieldErrors();
		Map<String, List<String>> map = new HashMap<>();
		for (FieldError error : listFieldsError) {
			String key = error.getField();
			String message = error.getDefaultMessage();
			if (map.containsKey(key)) {
				List<String> existingValue = map.get(key);
				existingValue.add(message);
			} else {
				map.put(key, Arrays.asList(message));
			}
		}
		return map;

		/*
		 * return rawErrors.getFieldErrors() .stream().collect( Collectors.groupingBy(
		 * FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage,
		 * Collectors.toList())));
		 */
	}
}
