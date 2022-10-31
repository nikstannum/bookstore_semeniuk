package com.belhard.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BookDto {
	private Long id;
	@NotBlank(message = "{general.errors.field.empty}")
	private String title;
	@NotBlank(message = "{general.errors.field.empty}")
	private String author;
	@NotBlank(message = "{general.errors.field.empty}")
	private String isbn;
	@NotBlank(message = "{general.errors.field.empty}")
	@Min(message = "{book.pages.min_pages}", value = 1)
	private Integer pages;
	@NotBlank(message = "{general.errors.field.empty}")
	@Min(message = "book.price.min_price", value = 0)
	private BigDecimal price;
	@NotNull(message = "{general.errors.field.empty}")
	private BookCoverDto coverDto;

	public enum BookCoverDto {
		SOFT, HARD, SPECIAL
	}

	public BookCoverDto getCoverDto() {
		return coverDto;
	}
}
