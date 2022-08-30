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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/books")
public class BooksCommand extends HttpServlet {
    private static final Logger log = LogManager.getLogger(BooksCommand.class);
    private final DataSource dataSource =DataSource.INSTANCE;
    private final BookService service = new BookServiceImpl(new BookDaoImpl(dataSource));
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BookDto> books = service.getAll();
        req.setAttribute("books", books);
        req.getRequestDispatcher("jsp/books.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            dataSource.close();
            log.info("dataSource successfully destroyed");
        } catch (Exception e) {
            log.error(e + " dataSource didn't destroy");
        }
    }
}
