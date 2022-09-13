package com.belhard.controller.command.impl.books;

import java.math.BigDecimal;

import com.belhard.controller.command.Command;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;
import com.belhard.service.dto.BookDto.BookCoverDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class UpdateBookCommand implements Command {

	private final BookService service;

	public UpdateBookCommand(BookService service) {
		this.service = service;
	}
	
	@Override
	public String execute(HttpServletRequest req) {
		Long id = Long.parseLong(req.getParameter("id"));
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String isbn = req.getParameter("isbn");
		Integer pages = Integer.valueOf(req.getParameter("pages"));
		BigDecimal price = BigDecimal.valueOf(Double.parseDouble(req.getParameter("price")));
		String cover = req.getParameter("cover");
		BookDto bookDto = new BookDto();
		bookDto.setId(id);
		bookDto.setTitle(title);
		bookDto.setAuthor(author);
		bookDto.setIsbn(isbn);
		bookDto.setPages(pages);
		bookDto.setPrice(price);
		bookDto.setCoverDto(BookCoverDto.valueOf(cover));
		BookDto updated = service.update(bookDto);
		req.setAttribute("book", updated);
		req.setAttribute("message", "book updated successfully");
		return "jsp/book/book.jsp";
	}

}
