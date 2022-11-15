package com.belhard.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice("com.com.belhard.controller.command.impl")
public class ExceptionAdvice {
	private final MessageSource messageSource;

	@ExceptionHandler
	public String execute(RuntimeException e, Model model, Locale locale) {
		model.addAttribute("message", messageSource.getMessage("error.unknown_error", null, locale));
		return "error";
	}

}
