package com.belhard.dao.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Book {
	private Long id;
	private String title;
	private String author;
	private String isbn;
	private Integer pages;
	private BigDecimal price;
	private BookCover cover;

	public enum BookCover {
		SOFT, HARD, SPECIAL
	}
}
