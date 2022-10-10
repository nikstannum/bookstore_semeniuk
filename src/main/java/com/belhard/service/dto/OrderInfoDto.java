package com.belhard.service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderInfoDto {

	private Long id;
	private OrderDto orderDto;
	private BookDto bookDto;
	private Integer bookQuantity;
	private BigDecimal bookPrice;
}
