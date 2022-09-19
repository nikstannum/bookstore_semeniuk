package com.belhard.dao;

import java.util.List;

import com.belhard.dao.entity.Order;

public interface OrderDao extends CrudDao<Long, Order> {
	
	public boolean removeRedundantDetails (List<Long> listId);
}
