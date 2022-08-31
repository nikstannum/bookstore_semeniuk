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
	private final BookService service = new BookServiceImpl(new BookDaoImpl(DataSource.INSTANCE));

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = getId(req, resp);
		if (id == null) {
			return;
		}
		BookDto dto;
		try {
			dto = service.get(id);
		} catch (Exception e) {
			resp.setStatus(500);
			resp.getWriter().write("Not exists book with id=" + id);
			return;
		}
		req.setAttribute("book", dto);
		req.getRequestDispatcher("jsp/book.jsp").forward(req, resp);
	}

	private Long getId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String idStr = req.getParameter("id");
		long id;
		try {
			id = Long.parseLong(idStr);
		} catch (NumberFormatException e) {
			resp.setStatus(404);
			resp.getWriter().write("invalid value");
			return null;
		}
		return id;
	}

	@Override
	public void destroy() {
		try {
			DataSource.INSTANCE.close();
			log.info("dataSource successfully destroyed");
		} catch (Exception e) {
			log.error(e + " dataSource didn't destroy");
		}
	}
}
