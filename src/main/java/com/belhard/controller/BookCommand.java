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
    private final DataSource dataSource = DataSource.INSTANCE;
    private final BookService service = new BookServiceImpl(new BookDaoImpl(dataSource));

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

    @Override
    public void destroy() { // FIXME: do me need this method?
        super.destroy();
        try {
            dataSource.close();
            log.info("dataSource successfully destroyed");
        } catch (Exception e) {
            log.error(e + " dataSource didn't destroy");
        }
    }
}
