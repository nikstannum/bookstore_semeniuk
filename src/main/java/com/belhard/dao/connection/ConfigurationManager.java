package com.belhard.dao.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ConfigurationManager {
	private final Properties properties;
	private static final String propsFile = "/application.properties";

	public ConfigurationManager() {
		properties = new Properties();
		try (InputStream input = getClass().getResourceAsStream(propsFile);) {
			properties.load(input);
		} catch (IOException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
