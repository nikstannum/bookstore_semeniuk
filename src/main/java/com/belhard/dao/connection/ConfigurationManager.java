package com.belhard.dao.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurationManager {
	public static final ConfigurationManager INSTANCE = new ConfigurationManager();
	private final Properties properties;
	private final String url;
	private final String password;
	private final String user;
	public static final String propsFile = "/application.properties";
	private static final Logger log = LogManager.getLogger(ConfigurationManager.class);
	

	public ConfigurationManager() {
		properties = new Properties();
		try (InputStream input = getClass().getResourceAsStream(propsFile);) {
			properties.load(input);
		} catch (IOException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
		url = properties.getProperty("url");
		password = properties.getProperty("password");
		user = properties.getProperty("user");
	}

	public String getUrl() {
		return url;
	}

	public String getPassword() {
		return password;
	}

	public String getUser() {
		return user;
	}
}
