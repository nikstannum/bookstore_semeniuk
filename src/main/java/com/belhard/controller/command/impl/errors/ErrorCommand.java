package com.belhard.controller.command.impl.errors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;

@Controller
public class ErrorCommand {

	@LogInvocation
	public String execute(HttpServletRequest req) {
		return "jsp/error.jsp";
	}
}
