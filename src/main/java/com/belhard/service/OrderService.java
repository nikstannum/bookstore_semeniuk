package com.belhard.service;

import java.util.Map;

import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.UserDto;

public interface OrderService extends CrudService<Long, OrderDto> {

	public OrderDto processCart(Map<Long, Integer> cart, UserDto userDto);
}
