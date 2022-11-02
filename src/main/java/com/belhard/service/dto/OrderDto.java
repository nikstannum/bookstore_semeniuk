package com.belhard.service.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
	private Long id;
	private UserDto userDto;
	private BigDecimal totalCost;
	@NotNull(message = "{general.errors.field.empty}")
	private StatusDto statusDto;
	private List<OrderInfoDto> detailsDto;

	public enum StatusDto {
		PENDING, PAID, DELIVERED, CANCELED
	}
}
