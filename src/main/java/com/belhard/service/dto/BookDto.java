package com.belhard.service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer pages;
    private BigDecimal price;
    private BookCoverDto coverDto;

    public enum BookCoverDto {
        SOFT, HARD, SPECIAL
    }

    public BookCoverDto getCoverDto() {
        return coverDto;
    }
}
