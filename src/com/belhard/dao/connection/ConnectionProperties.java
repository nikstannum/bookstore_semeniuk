package com.belhard.dao.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionProperties {
    private final String url;
    private final String password;
    private final String user;

    public static final String propsFile = "resources/application.properties";

    public ConnectionProperties() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(propsFile)) {
            properties.load(input);
        } catch (IOException e) {
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
