package com.belhard.service.dto;

import java.math.BigDecimal;
import java.util.List;

import com.belhard.dao.entity.OrderInfo;
import com.belhard.dao.entity.User;

import lombok.Data;

@Data
public class OrderDto {
	private Long id;
	private User user;
	private BigDecimal totalCost;
	private StatusDto status;
	private List<OrderInfo> details;

	public enum StatusDto {
		PENDING, PAID, DELIVERED, CANCELED
	}
}
