package com.belhard.service.dto;

import java.math.BigDecimal;

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

    public void setCoverDto(BookCoverDto coverDto) {
        this.coverDto = coverDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BookDto{" + "id=" + id + ", title='" + title + '\'' +
                ", author='" + author + '\'' + ", isbn='" + isbn + '\'' +
                ", pages=" + pages + ", price=" + price + ", cover=" + coverDto + '}';
    }
}
