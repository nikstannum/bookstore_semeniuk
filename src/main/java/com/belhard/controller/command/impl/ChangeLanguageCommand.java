package com.belhard.controller.command.impl;

import java.util.Locale;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ChangeLanguageCommand implements Command {

	@LogInvocation
	@Override
	public String execute(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String reqLanguage = req.getParameter("lang");
		Locale locale;
		if (reqLanguage.equalsIgnoreCase("ru")) {
			locale = new Locale("ru");
		} else {
			locale = Locale.ENGLISH;
		}
		String lang = locale.getLanguage();
		session.setAttribute("language", lang);
		return "index.jsp";
	}

}
