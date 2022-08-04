package com.belhard;

import com.belhard.beans.Book;
import com.belhard.dao.BookDao;
import com.belhard.dao.BookDaoImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/bookstore";
    public static final String USER = "postgres";
    public static final String PASSWORD = "root";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            BookDao bookDao = new BookDaoImpl(connection);
            Book book = bookDao.get(1);

            Book book1 = new Book();
            book1.setTitle(book.getTitle());
            book1.setAuthor(book.getAuthor());
            book1.setIsbn(book.getIsbn());
            book1.setPages(book.getPages());
            book1.setPrice(book.getPrice());

            System.out.println("DELETE " + bookDao.delete(1));
            System.out.println("All after delete:");
            bookDao.getAll().forEach(System.out::println);

            System.out.println("CREATE " + bookDao.create(book1));
            System.out.println("All after Create ");
            bookDao.getAll().forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
