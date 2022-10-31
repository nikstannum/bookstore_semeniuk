package com.belhard.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;

public class MyLocaleResolver extends CookieLocaleResolver {
	private Locale locale;
	private final Map<String, Locale> locales = fillLocales();

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String langParam = request.getParameter("lang");
		if (langParam != null) {
			locale = locales.get(langParam);
		}
		String acceptLang = request.getHeader("Accept-Language");
		String defaultLang = acceptLang.substring(0, 2);
		switch (defaultLang) {
		case "ru" -> {
			setDefaultLocale(locales.get(defaultLang));
		}
		case "en" -> {
			setDefaultLocale(locales.get(defaultLang));
		}
		default -> setDefaultLocale(Locale.ENGLISH);
		}
		return locale;
	}

	private Map<String, Locale> fillLocales() {
		Map<String, Locale> locales = new HashMap<>();
		locales.put("ru", new Locale("ru"));
		locales.put("en", Locale.ENGLISH);
		return locales;
	}

}
