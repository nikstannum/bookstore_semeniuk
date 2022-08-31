package com.belhard.dao.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurationManager {
	private final Properties properties;
	private static final String propsFile = "/application.properties";
	private static final Logger log = LogManager.getLogger(ConfigurationManager.class);
	public static final ConfigurationManager INSTANCE = new ConfigurationManager();

	private ConfigurationManager() {
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
