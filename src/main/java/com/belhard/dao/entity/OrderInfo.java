package com.belhard.dao.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderInfo {

	private Long id;
	private Long orderId;
	private Book book;
	private Integer bookQuantity;
	private BigDecimal bookPrice;
}
