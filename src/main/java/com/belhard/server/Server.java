package com.belhard.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {

    public static final String propsFile = "src/main/resources/application.properties";
    private static final Logger log = LogManager.getLogger(Server.class);
    private final int port;
    private final Controller controller;

    {
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream(propsFile)) {
            properties.load(in);
            log.debug("connection to input stream completed");
        } catch (IOException e) {
            log.error("no connection to input stream");
        }
        this.port = Integer.parseInt(properties.getProperty("port"));
    }

    public Server(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            log.debug("server created with port = {}", port);
            while (true) {
                try (Socket socket = server.accept()) {
                    String content = getRequestContent(socket);
                    if (content.isEmpty()) {
                        continue;
                    }
                    HTTPRequest request = processRequestContent(content);
                    HTTPResponse response = new HTTPResponse(Status.OK);
                    controller.process(request, response);
                    sendResponse(socket, response);
                }
            }
        } catch (IOException e) {
            log.error("server didn't create at the port = {}", port);
        }
    }

    private HTTPRequest processRequestContent(String request) {
        log.debug("start");
        int endOfLine = request.indexOf("\r\n");
        String firstLine = request.substring(0, endOfLine);
        String[] elements = firstLine.split(" ");
        String payload = elements[1];
        int indexOfQuestionMark = payload.indexOf("?");
        String url;
        String[] params;
        if (indexOfQuestionMark != -1) {
            url = payload.substring(1, indexOfQuestionMark);
            params = payload.substring(indexOfQuestionMark + 1).split("&");
        } else {
            url = payload.substring(1);
            params = new String[0];
        }
        HTTPRequest httpRequest = new HTTPRequest(url);
        for (String param : params) {
            String[] splitKeysAndValues = param.split("=");
            httpRequest.getParameters().put(splitKeysAndValues[0], splitKeysAndValues[1]);
        }
        return httpRequest;
    }

    private String getRequestContent(Socket socket) throws IOException {
        StringBuilder content = new StringBuilder();
        InputStream inputStream = socket.getInputStream();
        log.debug("start");
        int read;
        int previous = 0;
        while ((read = inputStream.read()) != -1) {
            char current = (char) read;
            content.append(current);
            if (current == '\r' && previous == '\n') {
                break;
            }
            previous = current;
        }
        return content.toString();
    }

    private void sendResponse(Socket socket, HTTPResponse response) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            log.debug("open PrintWriter in ");
            Status status = response.getStatus();
            out.println("HTTP/1.1 " + status.getCode() + " " + status.getMessage());
            out.println();
            out.println(response.getBody());
            out.println();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("didn't open PrintWriter");
        }
    }
}
