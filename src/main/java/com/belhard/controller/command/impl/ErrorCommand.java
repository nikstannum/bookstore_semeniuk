package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {

	@Override
	public String execute(HttpServletRequest req) {
		return "jsp/error.jsp";
	}
}
