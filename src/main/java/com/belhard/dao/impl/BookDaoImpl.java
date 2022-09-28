package com.belhard.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.belhard.dao.BookDao;
import com.belhard.dao.entity.Book;
import com.belhard.dao.entity.Book.BookCover;

import lombok.extern.log4j.Log4j2;

@Repository
public class BookDaoImpl implements BookDao {

	public static final String INSERT = "INSERT INTO books (title, author, isbn, pages, price, cover_id) "
					+ "VALUES (?, ?, ?, ?, ?, (SELECT c.cover_id FROM covers c WHERE c.name = ?))";
	public static final String GET_BY_ID = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price, c.name AS "
					+ "cover FROM books b "
					+ "JOIN covers c ON b.cover_id = c.cover_id WHERE b.book_id = ? AND b.deleted = false";
	public static final String GET_ALL = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price, c.name AS "
					+ "cover FROM books b " + "JOIN covers c ON b.cover_id = c.cover_id WHERE b.deleted = false";
	public static final String GET_ALL_PAGED = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price, c.name AS "
					+ "cover FROM books b JOIN covers c ON b.cover_id = c.cover_id "
					+ "WHERE b.deleted = false ORDER BY b.book_id LIMIT ? OFFSET ?";
	public static final String GET_BY_ISBN = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price, c.name "
					+ "AS cover FROM books b "
					+ "JOIN covers c ON b.cover_id = c.cover_id WHERE b.isbn = ? AND b.deleted = false";
	public static final String GET_BY_AUTHOR = "SELECT b.book_id, b.title, b.author, b.isbn, b.pages, b.price, c.name"
					+ " AS cover FROM books b "
					+ "JOIN covers c ON b.cover_id = c.cover_id WHERE b.author = ? AND b.deleted = false";
	public static final String GET_COUNT_ALL_BOOKS = "SELECT count(b.book_id) AS total from books b WHERE b.deleted ="
					+ " false";
	public static final String UPDATE = "UPDATE books SET title = ?, author = ?, isbn = ?, pages = ?, price = ?, "
					+ "cover_id = (SELECT c.cover_id FROM covers c WHERE c.name = ?) "
					+ "WHERE book_id = ? AND deleted = false";
	public static final String DELETE = "UPDATE books SET deleted = true WHERE book_id = ?";

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public BookDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Book create(Book book) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(INSERT);
				statement.setString(1, book.getTitle());
				statement.setString(2, book.getAuthor());
				statement.setString(3, book.getIsbn());
				statement.setInt(4, book.getPages());
				statement.setBigDecimal(5, book.getPrice());
				statement.setString(6, book.getCover().toString());
				return statement;
			}
		};
		jdbcTemplate.update(psc, keyHolder);
		Number num = keyHolder.getKey();
		if (num == null) {
			throw new RuntimeException("key is null");
		}
		Long id = num.longValue();
		return get(id);
	}

	@Override
	public Book get(Long id) {
		return jdbcTemplate.queryForObject(GET_BY_ID, this::mapRow, id);
	}

	@Override
	public List<Book> getAll() {
		return jdbcTemplate.query(GET_ALL, this::mapRow);
	}

	@Override
	public List<Book> getAll(int limit, long offset) {
		return jdbcTemplate.query(GET_ALL_PAGED, this::mapRow, limit, offset);
	}

	@Override
	public Book getBookByIsbn(String isbn) {
		return jdbcTemplate.queryForObject(GET_BY_ISBN, this::mapRow, isbn);
	}

	@Override
	public List<Book> getBooksByAuthor(String author) {
		return jdbcTemplate.query(GET_BY_AUTHOR, this::mapRow, author);
	}

	@Override
	public Book update(Book book) {
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(DELETE);
				statement.setString(1, book.getTitle());
				statement.setString(2, book.getAuthor());
				statement.setString(3, book.getIsbn());
				statement.setInt(4, book.getPages());
				statement.setBigDecimal(5, book.getPrice());
				statement.setString(6, book.getCover().toString());
				statement.setLong(7, book.getId());
				return statement;
			}
		};
		int rowUpdate = jdbcTemplate.update(psc);
		if (rowUpdate == 0) {
			throw new RuntimeException("Can't update book with id=" + book.getId());
		}
		return get(book.getId());
	}

	@Override
	public boolean delete(Long id) {
		return jdbcTemplate.update(DELETE, id) == 1;
	}

	@Override
	public long countAll() {
		ResultSetExtractor<Long> count = new ResultSetExtractor<>() {

			@Override
			public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getLong("total");
				}
				throw new RuntimeException("ERROR: count of books not definition");
			}
		};

		return jdbcTemplate.query(GET_COUNT_ALL_BOOKS, count);
	}

	private Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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
