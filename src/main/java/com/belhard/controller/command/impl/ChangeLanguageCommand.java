package com.belhard.controller.command.impl;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.command.Command;
import com.belhard.controller.util.MessageManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChangeLanguageCommand implements Command {
	private final MessageManager messageManager; // FIXME

	@LogInvocation
	@Override
	public String execute(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String reqLanguage = req.getParameter("lang");
		Locale locale; // FIXME
		if (reqLanguage.equalsIgnoreCase("ru")) {
			locale = new Locale("ru");
			messageManager.changeLocale(locale);
		} else {
			locale = Locale.ENGLISH;
			messageManager.changeLocale(locale);
		}
		String lang = locale.getLanguage();
		session.setAttribute("language", lang);
		return "index.jsp";
	}

}
