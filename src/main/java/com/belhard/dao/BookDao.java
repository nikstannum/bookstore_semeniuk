package main.java.com.belhard.dao;

import main.java.com.belhard.dao.entity.Book;
import java.util.List;

public interface BookDao {
    Book create(Book book);

    Book get(long id);

    List<Book> getAll();

    Book getBookByIsbn(String isbn);

    List<Book> getBooksByAuthor(String author);

    Book update(Book book);

    boolean delete(long id);

    int countAllBooks();
}
