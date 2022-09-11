package com.belhard.service.dto;

import java.math.BigDecimal;
import java.util.List;

import com.belhard.dao.entity.OrderInfo;
import com.belhard.dao.entity.User;

import lombok.Data;

@Data
public class OrderDto {
	private Long id;
	private UserDto userDto;
	private BigDecimal totalCost;
	private StatusDto statusDto;
	private List<OrderInfoDto> detailsDto;

	public enum StatusDto {
		PENDING, PAID, DELIVERED, CANCELED
	}
}
