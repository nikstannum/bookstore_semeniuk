package com.belhard.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.belhard.dao.OrderDao;
import com.belhard.dao.entity.Order;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class OrderServiceImpl implements OrderService {
	private OrderDao orderDao;

	public OrderServiceImpl(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	@Override
	public OrderDto create(OrderDto dto) {
		log.debug("Service method called successfully");
		Order order = toEntity(dto);
		return toDto(orderDao.create(order));
	}

	private OrderDto toDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setStatus(OrderDto.StatusDto.valueOf(order.getStatus().toString()));
		orderDto.setUser(order.getUser());
		orderDto.setTotalCost(order.getTotalCost());
		orderDto.setDetails(order.getDetails());
		return orderDto;
	}

	private Order toEntity(OrderDto dto) {
		Order order = new Order();
		order.setId(dto.getId());
		order.setUser(dto.getUser());
		order.setStatus(Order.Status.valueOf(dto.getStatus().toString()));
		BigDecimal totalCost = BigDecimal.ZERO;
		dto.getDetails().stream().map(elm -> elm.getBookPrice().multiply(BigDecimal.valueOf(elm.getBookQuantity())))
				.forEach(totalCost::add);
		order.setTotalCost(totalCost);
		order.setDetails(dto.getDetails());
		return order;
	}

	@Override
	public OrderDto get(Long id) {
		Order order = orderDao.get(id);
		if (order == null) {
			throw new RuntimeException("Couldn't find order with id: " + id);
		}
		return toDto(order);
	}

	@Override
	public List<OrderDto> getAll() {
		log.debug("Service method called successfully");
		return orderDao.getAll().stream().map(this::toDto).toList();
	}

	@Override
	public int countAll() {
		log.debug("Service method called successfully");
		return orderDao.countAll();
	}

	@Override
	public OrderDto update(OrderDto dto) { // TODO after realization creation
		log.debug("Service method called successfully");
		Order existing = orderDao.get(dto.getId());
		if (existing != null && existing.getStatus().toString().compareToIgnoreCase(Order.Status.DELIVERED.toString()) == 1) {
		}
		return null;
	}

	@Override
	public void delete(Long id) {
		log.debug("Service method called successfully");
		orderDao.delete(id);
	}
}