package com.belhard.controller;

import com.belhard.dao.connection.DataSource;
import com.belhard.dao.impl.BookDaoImpl;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;
import com.belhard.service.impl.BookServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/book")
public class BookCommand extends HttpServlet {
    private static final Logger log = LogManager.getLogger(BookCommand.class);
    private BookService service = new BookServiceImpl(new BookDaoImpl(DataSource.INSTANCE));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        try {
            Long id = Long.parseLong(idStr);
            BookDto dto = service.get(id);
            req.setAttribute("book", dto);
            req.getRequestDispatcher("jsp/book.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.setStatus(404);
            resp.getWriter().write("invalid value");
        } catch (RuntimeException e) {
            resp.setStatus(400);
            resp.getWriter().write("Not exists book with id=" + idStr);
        }
    }

//    @Override
//    public void process(HTTPRequest req, HTTPResponse res) {
//        log.debug("start");
//        StringBuilder strBuilderResponse = new StringBuilder();
//        String url = req.getUrl();
//        switch (url) {
//            case "books" -> getAllBooks(res, strBuilderResponse);
//            case "users" -> getAllUsers(res, strBuilderResponse);
//            case "book" -> getByParams(req, res, strBuilderResponse);
//            default -> {
//                res.setStatus(Status.NOT_FOUND);
//                res.setBody("Uncorrected request");
//            }
//
//        }
//    }
//
//    private void getByParams(HTTPRequest req, HTTPResponse res, StringBuilder response) {
//        Map<String, String> params = req.getParameters();
//        String id = params.get("id");
//        String isbn = params.get("isbn");
//        String author = params.get("author"); // FIXME author has firstName and lastName through space
//        if (id != null) { // TODO if id = -1? What do me do?
//            BookDto bookDto = bookService.get(Long.parseLong(id));
//            response.append(bookDto);
//            res.setBody(response.toString());
//            res.setStatus(Status.OK);
//        } else if (isbn != null) {
//            BookDto bookDto = bookService.getBookDtoByIsbn(isbn);
//            response.append(bookDto);
//            res.setBody(response.toString());
//            res.setStatus(Status.OK);
//        } else if (author != null) {
//            List<BookDto> bookDto = bookService.getBooksByAuthor(author);
//            for (BookDto bookElm : bookDto) {
//                response.append(bookElm).append('\n');
//            }
//            res.setStatus(Status.OK);
//            res.setBody(response.toString());
//        } else {
//            uncorrectedResponse(res);
//        }
//    }
//
//    private void uncorrectedResponse(HTTPResponse res) {
//        res.setStatus(Status.NOT_FOUND);
//        res.setBody("Uncorrected request");
//    }
//
//    private void getAllUsers(HTTPResponse res, StringBuilder response) {
//        List<UserDto> userDtoList = userService.getAll();
//        for (UserDto userElm : userDtoList) {
//            response.append(userElm).append('\n');
//        }
//        res.setStatus(Status.OK);
//        res.setBody(response.toString());
//    }
//
//    private void getAllBooks(HTTPResponse res, StringBuilder response) {
//        List<BookDto> bookDtoList = bookService.getAll();
//        for (BookDto bookElm : bookDtoList) {
//            response.append(bookElm).append("\n");
//        }
//        res.setStatus(Status.OK);
//        res.setBody(response.toString());
}
