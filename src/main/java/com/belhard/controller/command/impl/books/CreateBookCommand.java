package com.belhard.controller.command.impl.books;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;

import com.belhard.controller.command.Command;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;
import com.belhard.service.dto.BookDto.BookCoverDto;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CreateBookCommand implements Command {

	private final BookService service;

	public CreateBookCommand(BookService service) {
		this.service = service;
	}

	@Override
	public String execute(HttpServletRequest req) {
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String isbn = req.getParameter("isbn");
		Integer pages = Integer.valueOf(req.getParameter("pages"));
		BigDecimal price = BigDecimal.valueOf(Double.parseDouble(req.getParameter("price")));
		String cover = req.getParameter("cover");
		BookDto bookDto = new BookDto();
		bookDto.setTitle(title);
		bookDto.setAuthor(author);
		bookDto.setIsbn(isbn);
		bookDto.setPages(pages);
		bookDto.setPrice(price);
		bookDto.setCoverDto(BookCoverDto.valueOf(cover));
		BookDto created = service.create(bookDto);
		req.setAttribute("book", created);
		String message = "book created successfully";
		req.setAttribute("message", message);
		return "jsp/book/book.jsp";
	}
}
