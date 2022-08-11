package main.java.com.belhard.service.dto;

import main.java.com.belhard.dao.entity.Book;
import java.math.BigDecimal;
import java.util.Objects;

public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer pages;
    private BigDecimal price;


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        BookDto bookDto = (BookDto) o;
        return id.equals(bookDto.id) && isbn.equals(bookDto.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn);
    }

    @Override
    public String toString() {
        return "Book { " +
                "id = " + id +
                ", title = " + title + ", author = " + author +
                ", isbn = " + isbn +
                ", pages = " + pages +
                ", price = " + price +
                " }";
    }
}
