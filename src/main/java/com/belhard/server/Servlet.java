package com.belhard.server;

public interface Servlet {

    public void process(HTTPRequest request, HTTPResponse response);

}
