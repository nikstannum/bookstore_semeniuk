package com.belhard.server;

import java.util.HashMap;
import java.util.Map;

public class HTTPRequest {
    private final Map<String, String> parameters = new HashMap<>();
    private final String url;

    public HTTPRequest(String url) {
        super();
        this.url = url;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getUrl() {
        return url;
    }
}
