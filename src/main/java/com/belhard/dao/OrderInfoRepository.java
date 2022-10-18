package com.belhard.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.belhard.dao.entity.OrderInfo;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
	List<OrderInfo> getByOrderId(Long id);

}
