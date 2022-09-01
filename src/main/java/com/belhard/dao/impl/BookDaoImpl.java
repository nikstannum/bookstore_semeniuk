package com.belhard.dao.impl;

import com.belhard.dao.BookDao;
import com.belhard.dao.connection.DataSource;
import com.belhard.dao.entity.Book;
import com.belhard.dao.entity.Book.BookCover;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

	private static final Logger log = LogManager.getLogger(BookDaoImpl.class);

	public static final String INSERT = "INSERT INTO books (title, author, isbn, pages, price, cover_id) "
			+ "VALUES (?, ?, ?, ?, ?, (SELECT c.cover_id FROM covers c WHERE c.name = ?))";
	public static final String GET_BY_ID = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price, c.name AS " + "cover FROM books b "
			+ "JOIN covers c ON b.cover_id = c.cover_id WHERE b.book_id = ? AND b.deleted = false";
	public static final String GET_ALL = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price, c.name AS " + "cover FROM books b "
			+ "JOIN covers c ON b.cover_id = c.cover_id WHERE b.deleted = false";
	public static final String GET_BY_ISBN = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price, c.name " + "AS cover FROM books b "
			+ "JOIN covers c ON b.cover_id = c.cover_id WHERE b.isbn = ? AND b.deleted = false";
	public static final String GET_BY_AUTHOR = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price, c.name" + " AS cover FROM books b "
			+ "JOIN covers c ON b.cover_id = c.cover_id WHERE b.author = ? AND b.deleted = false";
	public static final String GET_COUNT_ALL_BOOKS = "SELECT count(b.book_id) AS total from books b WHERE b.deleted =" + " false";
	public static final String UPDATE = "UPDATE books SET title = ?, author = ?, isbn = ?, pages = ?, price = ?, "
			+ "cover_id = (SELECT c.cover_id FROM covers c WHERE c.name = ?) " + "WHERE book_id = ? AND deleted = false";
	public static final String DELETE = "UPDATE books SET deleted = true WHERE book_id = ?";

	private final DataSource dataSource;

	public BookDaoImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Book create(Book book) {
		try (Connection connection = dataSource.getFreeConnections();
				PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, book.getTitle());
			statement.setString(2, book.getAuthor());
			statement.setString(3, book.getIsbn());
			statement.setInt(4, book.getPages());
			statement.setBigDecimal(5, book.getPrice());
			statement.setString(6, book.getCover().toString());

			statement.executeUpdate();

			log.debug("database access completed successfully");

			ResultSet keys = statement.getGeneratedKeys();
			if (keys.next()) {
				long id = keys.getLong("book_id");
				return get(id);
			}
		} catch (SQLException e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public Book get(Long id) {
		try (Connection connection = dataSource.getFreeConnections(); PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			log.debug("database access completed successfully");
			if (resultSet.next()) {
				return processBook(resultSet);
			}
		} catch (SQLException e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public List<Book> getAll() {
		List<Book> list = new ArrayList<>();
		try (Connection connection = dataSource.getFreeConnections(); Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(GET_ALL);
			log.debug("database access completed successfully");
			while (resultSet.next()) {
				list.add(processBook(resultSet));
			}
			return list;
		} catch (SQLException e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public Book getBookByIsbn(String isbn) {
		try (Connection connection = dataSource.getFreeConnections(); PreparedStatement statement = connection.prepareStatement(GET_BY_ISBN)) {
			statement.setString(1, isbn);
			ResultSet resultSet = statement.executeQuery();
			log.debug("database access completed successfully");
			if (resultSet.next()) {
				return processBook(resultSet);
			}
		} catch (SQLException e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public List<Book> getBooksByAuthor(String author) {
		List<Book> list = new ArrayList<>();
		try (Connection connection = dataSource.getFreeConnections(); PreparedStatement statement = connection.prepareStatement(GET_BY_AUTHOR)) {
			statement.setString(1, author);
			ResultSet resultSet = statement.executeQuery();
			log.debug("database access completed successfully");
			while (resultSet.next()) {
				list.add(processBook(resultSet));
			}
			return list;
		} catch (SQLException e) {
			log.error(e);
		}
		return list;
	}

	@Override
	public Book update(Book book) {
		try (Connection connection = dataSource.getFreeConnections(); PreparedStatement statement = connection.prepareStatement(UPDATE)) {
			statement.setString(1, book.getTitle());
			statement.setString(2, book.getAuthor());
			statement.setString(3, book.getIsbn());
			statement.setInt(4, book.getPages());
			statement.setBigDecimal(5, book.getPrice());
			statement.setString(6, book.getCover().toString());
			statement.setLong(7, book.getId());
			statement.executeUpdate();
			log.debug("database access completed successfully");
			return get(book.getId());
		} catch (SQLException e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public boolean delete(Long id) {
		try (Connection connection = dataSource.getFreeConnections(); PreparedStatement statement = connection.prepareStatement(DELETE)) {
			statement.setLong(1, id);
			int rowsDelete = statement.executeUpdate();
			log.debug("database access completed successfully");
			return rowsDelete == 1;
		} catch (SQLException e) {
			log.error(e);
		}
		return false;
	}

	@Override
	public int countAll() {
		try (Connection connection = dataSource.getFreeConnections(); Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(GET_COUNT_ALL_BOOKS);
			log.debug("database access completed successfully");
			if (resultSet.next()) {
				return resultSet.getInt("total");
			}
		} catch (SQLException e) {
			log.error(e);
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
		book.setCover(BookCover.valueOf(resultSet.getString("cover")));
		return book;
	}
}
