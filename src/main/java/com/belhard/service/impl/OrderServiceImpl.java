package com.belhard.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.dao.BookDao;
import com.belhard.dao.OrderDao;
import com.belhard.dao.OrderInfoDao;
import com.belhard.dao.entity.Order;
import com.belhard.dao.entity.OrderInfo;
import com.belhard.service.BookService;
import com.belhard.service.OrderService;
import com.belhard.service.dto.BookDto;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.OrderInfoDto;
import com.belhard.service.dto.UserDto;
import com.belhard.serviceutil.Mapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class OrderServiceImpl implements OrderService {
	private final OrderDao orderDao;
	private final OrderInfoDao orderInfoDao;
	private final BookDao bookDao;


	public OrderServiceImpl(OrderDao orderDao, OrderInfoDao orderInfoDao, BookDao bookDao) {
		this.orderDao = orderDao;
		this.orderInfoDao = orderInfoDao;
		this.bookDao = bookDao;
	}

	@Override
	public OrderDto create(OrderDto dto) {
		log.debug("Service method called successfully");
		Order order = toEntity(dto);
		OrderDto orderDto = toDto(orderDao.create(order));
		Long orderDtoId = orderDto.getId();
		List<OrderInfoDto> infosDto = dto.getDetailsDto();
		List<OrderInfo> infosEntity = Mapper.INSTANCE.infosToEntity(infosDto, orderDtoId);
		infosEntity.stream().map(elm -> orderInfoDao.create(elm)).toList();
		infosDto = Mapper.INSTANCE.infosToDto(infosEntity);
		orderDto.setDetailsDto(infosDto);
		return orderDto;
	}

	private OrderDto toDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setStatusDto(OrderDto.StatusDto.valueOf(Order.Status.PENDING.toString()));
		orderDto.setUserDto(Mapper.INSTANCE.userToDto(order.getUser()));
		orderDto.setTotalCost(order.getTotalCost());
		orderDto.setDetailsDto(toDetailsDto(order.getDetails()));
		return orderDto;
	}

	private Order toEntity(OrderDto dto) {
		Order order = new Order();
		order.setUser(Mapper.INSTANCE.userToEntity(dto.getUserDto()));
		order.setStatus(Order.Status.valueOf(dto.getStatusDto().toString()));
		BigDecimal totalCost = dto.getTotalCost();
		dto.getDetailsDto().stream().map(elm -> elm.getBookPrice().multiply(BigDecimal.valueOf(elm.getBookQuantity())))
				.forEach(totalCost::add);
		order.setTotalCost(totalCost);
		order.setDetails(toDetails(dto.getDetailsDto()));
		return order;
	}

	private List<OrderInfoDto> toDetailsDto(List<OrderInfo> details) {
		List<OrderInfoDto> detailsDto = new ArrayList<>(details.size());
		for (OrderInfo elm : details) {
			OrderInfoDto dto = new OrderInfoDto();
			dto.setId(elm.getId());
			dto.setOrderDtoId(elm.getOrderId());
			dto.setBookDto(Mapper.INSTANCE.bookToDto(elm.getBook()));
			dto.setBookQuantity(elm.getBookQuantity());
			dto.setBookPrice(elm.getBookPrice());
			detailsDto.add(dto);
		}
		return detailsDto;
	}

	private List<OrderInfo> toDetails(List<OrderInfoDto> detailsDto) {
		List<OrderInfo> details = new ArrayList<>(detailsDto.size());
		for (OrderInfoDto elm : detailsDto) {
			OrderInfo entity = new OrderInfo();
			entity.setId(elm.getId());
			entity.setOrderId(elm.getOrderDtoId());
			entity.setBook(Mapper.INSTANCE.bookToEntity(elm.getBookDto()));
			entity.setBookQuantity(elm.getBookQuantity());
			entity.setBookPrice(elm.getBookPrice());
			details.add(entity);
		}
		return details;
	}

	public OrderDto processCart(Map<Long, Integer> cart, UserDto userDto) {
		return createOrderDto(cart, userDto);
	}

	private OrderDto createOrderDto(Map<Long, Integer> cart, UserDto userDto) {
		OrderDto orderDto = new OrderDto();
		orderDto.setUserDto(userDto);
		orderDto.setStatusDto(OrderDto.StatusDto.PENDING);
		List<OrderInfoDto> detailsDto = new ArrayList<>();

		cart.forEach((bookId, quantity) -> {
			OrderInfoDto orderInfoDto = new OrderInfoDto();
			BookDto bookDto = Mapper.INSTANCE.bookToDto(bookDao.get(bookId));
			orderInfoDto.setBookDto(bookDto);
			orderInfoDto.setBookPrice(bookDto.getPrice());
			orderInfoDto.setBookQuantity(quantity);
			detailsDto.add(orderInfoDto);
		});
		BigDecimal totalCost = BigDecimal.ZERO;
		for (Long key : cart.keySet()) {
			totalCost = totalCost.add(bookDao.get(key).getPrice().multiply(BigDecimal.valueOf(cart.get(key))));
		}
		orderDto.setTotalCost(totalCost);
		orderDto.setDetailsDto(detailsDto);
		return orderDto;
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
	public List<OrderDto> getAll(Paging paging) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countAll() {
		log.debug("Service method called successfully");
		return orderDao.countAll();
	}

	@Override
	public OrderDto update(OrderDto dto) { // TODO after realization creation
		log.debug("Service method called successfully");
		Order existing = orderDao.get(dto.getId());
		if (existing != null
				&& existing.getStatus().toString().compareToIgnoreCase(Order.Status.DELIVERED.toString()) == 1) {
		}
		return null;
	}

	@Override
	public void delete(Long id) {
		log.debug("Service method called successfully");
		orderDao.delete(id);
	}
}