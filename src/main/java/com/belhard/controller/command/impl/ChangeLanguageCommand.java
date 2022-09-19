package com.belhard.controller.command.impl;

import java.util.Locale;

import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {

	@Override
	public String execute(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String language = req.getParameter("lang");
		if (language.equalsIgnoreCase("ru")) {
			Locale locale = new Locale("ru");
//			String lang = locale.getLanguage();
			session.setAttribute(language, locale);
		} else {
			Locale locale = Locale.ENGLISH;
//			String lang = locale.getLanguage();
			session.setAttribute(language, locale);
		}
		return "index.jsp";
	}

}
