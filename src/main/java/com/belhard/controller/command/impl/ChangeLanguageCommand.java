package com.belhard.controller.command.impl;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.MessageManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChangeLanguageCommand {
	private final MessageManager messageManager; // FIXME

	@RequestMapping("change_language")
	@LogInvocation
	public String execute(@RequestParam String lang, HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
		HttpSession session = req.getSession();
		Locale locale; // FIXME
		if (lang.equalsIgnoreCase("ru")) {
			locale = new Locale("ru");
			messageManager.changeLocale(locale);
		} else {
			locale = Locale.ENGLISH;
			messageManager.changeLocale(locale);
		}
		String language = locale.getLanguage();
		session.setAttribute("language", language);
		return "index";
	}

}
