package com.belhard.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.belhard")
public class ExceptionAdvice {

	@ExceptionHandler
	public String execute(RuntimeException e, Model model) {
		model.addAttribute("message", "Unknown error");
		return "error";
	}

}
