package com.belhard.dao;

import com.belhard.entity.Book;
import com.belhard.util.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    public static final String INSERT = "INSERT INTO books (title, author, isbn, pages, price) VALUES (?, ?, ?, ?, ?)";
    public static final String GET_BY_ID = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price from books b WHERE b.book_id = ?";
    public static final String GET_ALL = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price FROM books b";
    public static final String GET_BY_ISBN = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price FROM books b WHERE b.isbn = ?";
    public static final String GET_BY_AUTHOR = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price FROM books b WHERE b.author = ?";
    public static final String GET_COUNT_ALL_BOOKS = "SELECT count(b.book_id) AS total from books b";
    public static final String UPDATE = "UPDATE books SET title = ?, author = ?, isbn = ?, pages = ?, price = ? WHERE book_id = ?";
    public static final String DELETE = "DELETE FROM books b WHERE b.book_id = ?";

    private final DataSource dataSource;

    public BookDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Book create(Book book) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setInt(4, book.getPages());
            statement.setBigDecimal(5, book.getPrice());
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                Long id = keys.getLong("book_id");
                return get(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book get(long id) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return processBook(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> list = new ArrayList<>();
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_ALL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(processBook(resultSet));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_BY_ISBN);
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return processBook(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> list = new ArrayList<>();
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_BY_AUTHOR);
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(processBook(resultSet));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Book update(Book book) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setInt(4, book.getPages());
            statement.setBigDecimal(5, book.getPrice());
            statement.setLong(6, book.getId());
            statement.executeUpdate();

            return get(book.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        PreparedStatement statement = null;
        try {
            statement = dataSource.getConnection().prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDelete = statement.executeUpdate();
            return rowsDelete == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int countAllBooks() {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_COUNT_ALL_BOOKS);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("ERROR: count of books not definition");
    }

    private Book processBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("book_id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setPages(resultSet.getInt("pages"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
