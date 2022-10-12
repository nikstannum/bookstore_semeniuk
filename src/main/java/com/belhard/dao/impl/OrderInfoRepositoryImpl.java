package com.belhard.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.belhard.aop.LogInvocation;
import com.belhard.dao.OrderInfoRepository;
import com.belhard.dao.entity.OrderInfo;

@Repository
@Transactional
public class OrderInfoRepositoryImpl implements OrderInfoRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	@LogInvocation
	public OrderInfo create(OrderInfo entity) {
		manager.persist(entity);
		return entity;
	}

	@Override
	@LogInvocation
	public List<OrderInfo> getByOrderId(Long id) {
		TypedQuery<OrderInfo> query = manager.createQuery("from OrderInfo where order_id = :order_id", OrderInfo.class);
		query.setParameter("order_id", id);
		return query.getResultList();
	}

	@Override
	@LogInvocation
	public OrderInfo get(Long id) {
		return manager.find(OrderInfo.class, id);
	}

	@Override
	@LogInvocation
	public List<OrderInfo> getAll() {
		return manager.createQuery("from OrderInfo", OrderInfo.class).getResultList();
	}

	@Override
	@LogInvocation
	public List<OrderInfo> getAll(int limit, long offset) {
		TypedQuery<OrderInfo> query = manager.createQuery("from OrderInfo", OrderInfo.class);
		query.setFirstResult((int) offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	@LogInvocation
	public long countAll() {
		TypedQuery<Long> query = manager.createQuery(
						"SELECT count(order_infos_id) from OrderInfo WHERE deleted = :deleted", Long.class);
		query.setParameter("deleted", false);
		if (query.getResultList().isEmpty()) {
			throw new RuntimeException("count of details of orders was not definition");
		}
		return query.getResultStream().findAny().get();
	}

	@Override
	@LogInvocation
	public OrderInfo update(OrderInfo entity) {
		OrderInfo fromDb = manager.find(OrderInfo.class, entity.getId());
		fromDb.setBook(entity.getBook());
		fromDb.setBookPrice(entity.getBookPrice());
		fromDb.setBookQuantity(entity.getBookQuantity());
		fromDb.setOrder(entity.getOrder());
		return fromDb;
	}

	@Override
	public boolean removeRedundantDetails(List<Long> listId) {
		for (Long id : listId) {
			boolean result = delete(id);
			if (result == false) {
				throw new RuntimeException("Failed to delete item details");
			}
		}
		return true;
	}

	@Override
	@LogInvocation
	public boolean delete(Long id) {
		OrderInfo info = manager.find(OrderInfo.class, id);
		info.setDeleted(true);
		return true;
	}
}