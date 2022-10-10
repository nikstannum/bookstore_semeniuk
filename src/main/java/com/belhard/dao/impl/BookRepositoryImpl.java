package com.belhard.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.belhard.dao.BookRepository;
import com.belhard.dao.entity.Book;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Book create(Book book) {
		manager.persist(book);
		return book;
	}

	@Override
	public Book get(Long id) {
		return manager.find(Book.class, id);
	}

	@Override
	public List<Book> getAll() {
		return manager.createQuery("from Book", Book.class).getResultList();
	}

	@Override
	public List<Book> getAll(int limit, long offset) {
		TypedQuery<Book> query = manager.createQuery("from Book", Book.class);
		query.setFirstResult((int) offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public Book getBookByIsbn(String isbn) {
		TypedQuery<Book> query = manager.createQuery("from Book where isbn = :isbn", Book.class);
		query.setParameter("isbn", isbn);
		if (query.getResultList().isEmpty()) {
			return null;
		}
		Book book = query.getResultStream().findAny().get();
		return book;
	}

	@Override
	public List<Book> getBooksByAuthor(String author) {
		TypedQuery<Book> query = manager.createQuery("from Book where author = :author", Book.class);
		query.setParameter("author", author);
		return query.getResultList();
	}

	@Override
	public Book update(Book book) {
		Book fromDb = manager.find(Book.class, book.getId());
		fromDb.setAuthor(book.getAuthor());
		fromDb.setTitle(book.getTitle());
		fromDb.setIsbn(book.getIsbn());
		fromDb.setPages(book.getPages());
		fromDb.setPrice(book.getPrice());
		fromDb.setCover(book.getCover());
		return fromDb;
	}

	@Override
	public boolean delete(Long id) {
		Book book = manager.find(Book.class, id);
		book.setDeleted(true);
		return true;
	}

	@Override
	public long countAll() {
		TypedQuery<Long> query = manager.createQuery("SELECT count(book_id) from Book", Long.class);
		if (query.getResultList().isEmpty()) {
			throw new RuntimeException("count of books was not definition");
		}
		return query.getResultStream().findAny().get();
	}
}