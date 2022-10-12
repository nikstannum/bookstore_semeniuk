package com.belhard.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.belhard.dao.UserRepository;
import com.belhard.dao.entity.User;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public User create(User user) {
		manager.persist(user);
		return user;
	}

	@Override
	public User get(Long id) {
		return manager.find(User.class, id);
	}

	@Override
	public List<User> getAll() {
		return manager.createQuery("from User", User.class).getResultList();
	}

	@Override
	public List<User> getAll(int limit, long offset) {
		TypedQuery<User> query = manager.createQuery("from User", User.class);
		query.setFirstResult((int) offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public User getUserByEmail(String email) {
		TypedQuery<User> query = manager.createQuery("from User where email = :email", User.class);
		query.setParameter("email", email);
		if (query.getResultList().isEmpty()) {
			return null;
		}
		User user = query.getResultStream().findAny().get();
		return user;
	}

	@Override
	public List<User> getUsersByLastName(String lastName) {
		TypedQuery<User> query = manager.createQuery("from User where last_name = :lastName", User.class);
		query.setParameter("lastName", lastName);
		return query.getResultList();
	}

	@Override
	public long countAll() {
		TypedQuery<Long> query = manager.createQuery("SELECT count(user_id) from User", Long.class);
		if (query.getResultList().isEmpty()) {
			throw new RuntimeException("count of users was not definition");
		}
		return query.getResultStream().findAny().get();
	}

	@Override
	public User update(User user) {
		User fromDb = manager.find(User.class, user.getId());
		fromDb.setEmail(user.getEmail());
		fromDb.setFirstName(user.getFirstName());
		fromDb.setLastName(user.getLastName());
		fromDb.setPassword(user.getPassword());
		fromDb.setUserRole(user.getRole());
		return fromDb;
	}

	@Override
	public boolean delete(Long id) {
		User user = manager.find(User.class, id);
		user.setDeleted(true);
		return true;
	}
}
