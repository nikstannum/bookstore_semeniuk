package com.belhard.controller.command.impl;

import org.springframework.stereotype.Controller;

import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
		return "index.jsp";
	}

}
