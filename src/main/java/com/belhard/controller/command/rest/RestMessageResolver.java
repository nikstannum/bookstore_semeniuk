package com.belhard.controller.command.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/messages")
public class RestMessageResolver {
	private final MessageSource messageSource;

	@PostMapping
	public Map<String, String> getMessage(@RequestBody String[] keys) {
		Map<String, String> messages = new HashMap<>();
		for (String key : keys) {
			messages.put(key.replace('.', '_'), messageSource.getMessage(key, null, LocaleContextHolder.getLocale()));
		}
		return messages;
	}

}
