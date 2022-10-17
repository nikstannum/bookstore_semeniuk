package com.belhard.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.belhard.aop.LogInvocation;
import com.belhard.dao.OrderRepository;
import com.belhard.dao.entity.Order;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	@LogInvocation
	public Order create(Order entity) {
		manager.persist(entity);
		return entity;
	}

	@Override
	@LogInvocation
	public Order get(Long id) {
		return manager.find(Order.class, id);
	}

	@Override
	@LogInvocation
	public List<Order> getAll() {
		return manager.createQuery("from Order", Order.class).getResultList();
	}

	@Override
	@LogInvocation
	public List<Order> getAll(int limit, long offset) {
		TypedQuery<Order> query = manager.createQuery("from Order", Order.class);
		query.setFirstResult((int) offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	@LogInvocation
	public long countAll() {
		TypedQuery<Long> query = manager.createQuery("SELECT count(order_id) from Order", Long.class);
		if (query.getResultList().isEmpty()) {
			throw new RuntimeException("count of details of orders was not definition");
		}
		return query.getResultStream().findAny().get();
	}

	@Override
	@LogInvocation
	public Order update(Order entity) {
		manager.merge(entity);
		return entity;
	}

	@Override
	@LogInvocation
	public boolean delete(Long id) {
		Order order = manager.find(Order.class, id);
		order.setDeleted(true);
		return true;
	}
}
