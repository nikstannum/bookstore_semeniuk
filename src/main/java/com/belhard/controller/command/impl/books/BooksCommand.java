package com.belhard.controller.command.impl.books;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.belhard.controller.command.Command;
import com.belhard.controller.util.PagingUtil;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

public class BooksCommand implements Command {
	private final BookService service;
	private final PagingUtil pagingUtil;

	public BooksCommand(BookService service, PagingUtil pagingUtil) {
		this.service = service;
		this.pagingUtil = pagingUtil;
	}

	@Override
	public String execute(HttpServletRequest req) {
		Paging paging = pagingUtil.getPaging(req);
		List<BookDto> books = service.getAll(paging);
		long totalEntities = service.countAll();
		long totalPages = pagingUtil.getTotalPages(totalEntities, paging.getLimit());
		req.setAttribute("books", books);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", paging.getPage());
		return "jsp/book/books.jsp";
	}
}
