package com.belhard.controller.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component
//@Scope("prototype")
public class MessageManager {
	private static final String BUNDLE_NAME = "messages";
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

	public void changeLocale(Locale locale) {
		resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
	}

	public String getMessage(String key) {
		return resourceBundle.getString(key);
	}

	@Override
	public String toString() { // FIXME delete
		return "MessageManager [locale=" + resourceBundle.getLocale() + "]";
	}

}
