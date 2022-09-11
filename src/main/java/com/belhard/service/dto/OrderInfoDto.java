package com.belhard.service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderInfoDto {

	private Long id;
	private Long orderDtoId;
	private BookDto bookDto;
	private Integer bookQuantity;
	private BigDecimal bookPrice;
}
