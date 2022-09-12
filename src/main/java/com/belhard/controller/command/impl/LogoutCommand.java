package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
		return "index.jsp";
	}

}
