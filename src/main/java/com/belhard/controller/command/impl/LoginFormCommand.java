package com.belhard.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.command.Command;

@Controller
public class LoginFormCommand implements Command {

	@LogInvocation
	@Override
	public String execute(HttpServletRequest req) {
		return "jsp/loginForm.jsp";
	}

}
