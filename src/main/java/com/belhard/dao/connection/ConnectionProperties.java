package com.belhard.dao.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionProperties {
    private final String url;
    private final String password;
    private final String user;

    public static final String propsFile = "src/main/resources/application.properties";
    private static final Logger logger = LogManager.getLogger(ConnectionProperties.class);

    public ConnectionProperties() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(propsFile)) {
            properties.load(input);
        } catch (IOException e) {
            logger.error(e);
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
