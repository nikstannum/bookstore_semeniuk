package com.belhard.service.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInfoDto {

	private Long id;
	@JsonIgnore
	private OrderDto orderDto;
	private BookDto bookDto;
	private Integer bookQuantity;
	private BigDecimal bookPrice;
}
