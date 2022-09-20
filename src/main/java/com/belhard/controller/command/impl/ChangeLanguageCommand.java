package com.belhard.controller.command.impl;

import java.util.Locale;

import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {

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
