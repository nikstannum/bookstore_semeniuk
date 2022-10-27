package com.belhard.controller.command.impl;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.MessageManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChangeLanguageCommand {
	private final MessageManager messageManager; // FIXME

	@RequestMapping("change_language")
	@LogInvocation
	public String execute(Locale locale, HttpSession session) throws IOException {
		messageManager.changeLocale(locale);
		String language = locale.getLanguage();
		session.setAttribute("language", language);
		return "index";
	}

}
