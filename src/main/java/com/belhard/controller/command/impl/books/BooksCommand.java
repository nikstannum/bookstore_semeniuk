package com.belhard.controller.command.impl.books;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.command.Command;
import com.belhard.controller.util.PagingUtil;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;

@Controller
public class BooksCommand implements Command {
	private final BookService service;
	private final PagingUtil pagingUtil;

	public BooksCommand(BookService service, PagingUtil pagingUtil) {
		this.service = service;
		this.pagingUtil = pagingUtil;
	}

	@LogInvocation
	@Override
	@RequestMapping("books")
	public String execute(HttpServletRequest req) {
		Paging paging = pagingUtil.getPaging(req);
		List<BookDto> books = service.getAll(paging);
		long totalEntities = service.countAll();
		long totalPages = pagingUtil.getTotalPages(totalEntities, paging.getLimit());
		req.setAttribute("books", books);
		req.setAttribute("currentCommand", "books");
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", paging.getPage());
		return "jsp/book/books.jsp";
	}

}
