package com.belhard.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;

public class MyLocaleResolver extends CookieLocaleResolver {

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Locale locale;
		String acceptLang = request.getHeader("Accept-Language");
		String realLang = acceptLang.substring(0, 2);
		switch (realLang) {
		case "ru" -> {
			locale = new Locale("ru");
		}
		case "en" -> locale = new Locale("en");
		default -> locale = (Locale.ENGLISH);
		}
		return locale;
	}
}
