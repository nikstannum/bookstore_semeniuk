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
import java.util.List;

@WebServlet("/books")
public class BooksCommand extends HttpServlet {
    private final BookService service = new BookServiceImpl(new BookDaoImpl(DataSource.INSTANCE));
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BookDto> books = service.getAll();
        req.setAttribute("books", books);
        req.getRequestDispatcher("jsp/books.jsp").forward(req, resp);
    }
}
