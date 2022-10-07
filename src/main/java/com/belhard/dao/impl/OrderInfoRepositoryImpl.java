package com.belhard.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.belhard.dao.OrderInfoRepository;
import com.belhard.dao.entity.OrderInfo;

@Repository
@Transactional
public class OrderInfoRepositoryImpl implements OrderInfoRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public OrderInfo create(OrderInfo entity) {
		manager.persist(entity);
		return entity;
	}

	@Override
	public List<OrderInfo> getByOrderId(Long id) {
		TypedQuery<OrderInfo> query = manager.createQuery("from OrderInfo where order_id = :order_id", OrderInfo.class);
		query.setParameter("order_id", id);
		return query.getResultList();
	}

	@Override
	public OrderInfo get(Long id) {
		return manager.find(OrderInfo.class, id);
	}

	@Override
	public List<OrderInfo> getAll() {
		return manager.createQuery("from OrderInfo", OrderInfo.class).getResultList();
	}

	@Override
	public List<OrderInfo> getAll(int limit, long offset) {
		TypedQuery<OrderInfo> query = manager.createQuery("from OrderInfo", OrderInfo.class);
		query.setFirstResult((int) offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
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
	public OrderInfo update(OrderInfo entity) {
		OrderInfo fromDb = manager.find(OrderInfo.class, entity.getId());
		fromDb.setBook(entity.getBook());
		fromDb.setBookPrice(entity.getBookPrice());
		fromDb.setBookQuantity(entity.getBookQuantity());
		fromDb.setOrderId(entity.getOrderId());
		return entity;
	}

	@Override
	public boolean delete(Long id) {
		OrderInfo info = manager.find(OrderInfo.class, id);
		info.setDeleted(true);
		return true;
	}
}