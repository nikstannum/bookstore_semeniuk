package com.belhard.dao;

import java.util.List;

import com.belhard.dao.entity.OrderInfo;

public interface OrderInfoRepository extends CrudRepository<Long, OrderInfo> {
	List<OrderInfo> getByOrderId(Long id);

}
