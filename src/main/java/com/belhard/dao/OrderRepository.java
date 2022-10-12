package com.belhard.dao;

import java.util.List;

import com.belhard.dao.entity.Order;

public interface OrderRepository extends CrudRepository<Long, Order> {
	
	public boolean removeRedundantDetails (List<Long> listId);
}
