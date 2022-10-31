package com.belhard.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.OrderInfoDto;
import com.belhard.service.dto.UserDto;

public interface OrderService extends CrudService<Long, OrderDto> {

	public OrderDto processCart(Map<Long, Integer> cart, UserDto userDto, Locale locale);

	public OrderDto preProcessUpdate(OrderDto orderDto, List<OrderInfoDto> list, Long detailsDtoId, boolean increaseQuantity, Locale locale);
	
}
