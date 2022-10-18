package com.belhard.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.belhard.dao.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
