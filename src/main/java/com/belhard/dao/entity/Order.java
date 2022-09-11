package com.belhard.dao.entity;

import java.math.BigDecimal;
import java.util.List;

import com.belhard.service.dto.UserDto;

import lombok.Data;

@Data
public class Order {

	private Long id;
	private User user;
	private BigDecimal totalCost;
	private Status status;
	private List<OrderInfo> details;

	public enum Status {
		PENDING, PAID, DELIVERED, CANCELED
	}
}
