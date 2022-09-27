package com.belhard.controller.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
//@Component
public class MessageManager {
	private static final String BUNDLE_NAME = "messages";
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
	private Locale locale;
	
	public MessageManager(Locale locale) {
		this.locale = locale;
	}
	
	public String getMessage(String key) {
		return resourceBundle.getString(key);
	}
}
