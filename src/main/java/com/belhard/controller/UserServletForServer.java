package com.belhard.controller;

import com.belhard.server.Servlet;
import com.belhard.server.HTTPRequest;
import com.belhard.server.HTTPResponse;
import com.belhard.server.Status;
import com.belhard.service.BookService;
import com.belhard.service.UserService;
import com.belhard.service.dto.BookDto;
import com.belhard.service.dto.UserDto;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServletForServer implements Servlet {
    private BookService bookService;
    private UserService userService;
    private static final Logger log = LogManager.getLogger(UserServletForServer.class);

    public UserServletForServer(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @Override
    public void process(HTTPRequest req, HTTPResponse res) {
        log.debug("start");
        StringBuilder strBuilderResponse = new StringBuilder();
        String url = req.getUrl();
        switch (url) {
            case "books" -> getAllBooks(res, strBuilderResponse);
            case "users" -> getAllUsers(res, strBuilderResponse);
            case "book" -> getByParams(req, res, strBuilderResponse);
            default -> {
                res.setStatus(Status.NOT_FOUND);
                res.setBody("Uncorrected request");
            }

        }
    }

    private void getByParams(HTTPRequest req, HTTPResponse res, StringBuilder response) {
        Map<String, String> params = req.getParameters();
        String id = params.get("id");
        String isbn = params.get("isbn");
        String author = params.get("author"); // FIXME author has firstName and lastName through space
        if (id != null) { // TODO if id = -1? What do me do?
            BookDto bookDto = bookService.get(Long.parseLong(id));
            response.append(bookDto);
            res.setBody(response.toString());
            res.setStatus(Status.OK);
        } else if (isbn != null) {
            BookDto bookDto = bookService.getBookDtoByIsbn(isbn);
            response.append(bookDto);
            res.setBody(response.toString());
            res.setStatus(Status.OK);
        } else if (author != null) {
            List<BookDto> bookDto = bookService.getBooksByAuthor(author);
            for (BookDto bookElm : bookDto) {
                response.append(bookElm).append('\n');
            }
            res.setStatus(Status.OK);
            res.setBody(response.toString());
        } else {
            uncorrectedResponse(res);
        }
    }

    private void uncorrectedResponse(HTTPResponse res) {
        res.setStatus(Status.NOT_FOUND);
        res.setBody("Uncorrected request");
    }

    private void getAllUsers(HTTPResponse res, StringBuilder response) {
        List<UserDto> userDtoList = userService.getAll();
        for (UserDto userElm : userDtoList) {
            response.append(userElm).append('\n');
        }
        res.setStatus(Status.OK);
        res.setBody(response.toString());
    }

    private void getAllBooks(HTTPResponse res, StringBuilder response) {
        List<BookDto> bookDtoList = bookService.getAll();
        for (BookDto bookElm : bookDtoList) {
            response.append(bookElm).append("\n");
        }
        res.setStatus(Status.OK);
        res.setBody(response.toString());
    }
}
