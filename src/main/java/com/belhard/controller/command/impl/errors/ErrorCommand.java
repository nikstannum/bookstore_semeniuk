package com.belhard.controller.command.impl.errors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

import com.belhard.aop.LogInvocation;

public class ErrorCommand {
	@RequestMapping("/error")
	@LogInvocation
	public String execute(HttpServletRequest req) {
		return "error";
	}
}
