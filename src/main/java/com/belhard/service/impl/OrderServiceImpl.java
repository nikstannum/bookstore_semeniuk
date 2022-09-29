package com.belhard.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.dao.BookDao;
import com.belhard.dao.OrderDao;
import com.belhard.dao.entity.Order;
import com.belhard.dao.entity.OrderInfo;
import com.belhard.service.OrderService;
import com.belhard.service.dto.BookDto;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.OrderInfoDto;
import com.belhard.service.dto.UserDto;
import com.belhard.serviceutil.Mapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OrderServiceImpl implements OrderService {
	private final OrderDao orderDao;
	private final BookDao bookDao;

	@Autowired
	public OrderServiceImpl(OrderDao orderDao, BookDao bookDao) {
		this.orderDao = orderDao;
		this.bookDao = bookDao;
	}

	@Override
	public OrderDto get(Long id) {
		Order order = orderDao.get(id);
		if (order == null) {
			throw new RuntimeException("Couldn't find order with id: " + id);
		}
		return toDto(order);
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
	public List<OrderDto> getAll() {
		log.debug("Service method called successfully");
		return orderDao.getAll().stream().map(this::toDto).toList();
	}

	@Override
	public List<OrderDto> getAll(Paging paging) {
		int limit = paging.getLimit();
		long offset = paging.getOffset();
		log.debug("Service method called successfully");
		return orderDao.getAll(limit, offset).stream().map(this::toDto).toList();
	}

	@Override
	public long countAll() {
		log.debug("Service method called successfully");
		return orderDao.countAll();
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

	@Override
	public OrderDto create(OrderDto dto) {
		log.debug("Service method called successfully");
		Order order = toEntity(dto);
		OrderDto orderDto = toDto(orderDao.create(order));
		return orderDto;
	}

	private Order toEntity(OrderDto dto) {
		Order order = new Order();
		order.setId(dto.getId());
		order.setUser(Mapper.INSTANCE.userToEntity(dto.getUserDto()));
		order.setStatus(Order.Status.valueOf(dto.getStatusDto().toString()));
		BigDecimal totalCost = dto.getTotalCost();
		dto.getDetailsDto().stream().map(elm -> elm.getBookPrice().multiply(BigDecimal.valueOf(elm.getBookQuantity())))
						.forEach(totalCost::add);
		order.setTotalCost(totalCost);
		order.setDetails(toDetails(dto.getDetailsDto()));
		return order;
	}

	private OrderDto toDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setStatusDto(OrderDto.StatusDto.valueOf(order.getStatus().toString()));
		orderDto.setUserDto(Mapper.INSTANCE.userToDto(order.getUser()));
		orderDto.setTotalCost(order.getTotalCost());
		orderDto.setDetailsDto(toDetailsDto(order.getDetails()));
		return orderDto;
	}

	@Override
	public OrderDto update(OrderDto dto) {
		log.debug("Service method called successfully");
		Order existing = orderDao.get(dto.getId());
		if (existing == null) {
			throw new RuntimeException("order wasn't found");
		}
		List<OrderInfo> infosFromDB = existing.getDetails();
		List<Long> listInfoIdFromDB = infosFromDB.stream().map(elm -> elm.getId()).toList();
		List<OrderInfo> infos = toDetails(dto.getDetailsDto());
		List<Long> listInfoIdFromRequest = infos.stream().map(elm -> elm.getId()).toList();
		List<Long> infoIdForDelete = new ArrayList<>();
		for (Long elm : listInfoIdFromDB) {
			if (!listInfoIdFromRequest.contains(elm)) {
				infoIdForDelete.add(elm);
			}
		}
		orderDao.removeRedundantDetails(infoIdForDelete);
		OrderDto orderDtoUpdated = toDto(orderDao.update(toEntity(dto)));
		return orderDtoUpdated;
	}

	@Override
	public OrderDto preProcessUpdate(OrderDto orderDto, List<OrderInfoDto> list, Long detailsDtoId,
					boolean increaseQuantity) {
		List<Integer> listOfIndexOfSubjectToRemoval = new ArrayList<>();
		for (OrderInfoDto elm : list) {
			if (elm.getId() == detailsDtoId) {
				if (increaseQuantity) {
					elm.setBookQuantity(elm.getBookQuantity() + 1);
					BigDecimal bookPriceFromCatalog = bookDao.get(elm.getBookDto().getId()).getPrice();
					BigDecimal oldCost = orderDto.getTotalCost();
					BigDecimal updatedCost = oldCost.add(bookPriceFromCatalog);
					orderDto.setTotalCost(updatedCost);
				} else {
					elm.setBookQuantity(elm.getBookQuantity() - 1);
					BigDecimal bookPriceFromOrder = elm.getBookPrice();
					BigDecimal oldCost = orderDto.getTotalCost();
					BigDecimal updatedCost = oldCost.subtract(bookPriceFromOrder);
					orderDto.setTotalCost(updatedCost);
					if (elm.getBookQuantity() == 0) {
						listOfIndexOfSubjectToRemoval.add(list.indexOf(elm));
					}
				}
			}
		}
		for (Integer elm : listOfIndexOfSubjectToRemoval) {
			int i = elm;
			list.remove(i);
		}
		orderDto.setDetailsDto(list);
		return orderDto;
	}

	@Override
	public void delete(Long id) {
		log.debug("Service method called successfully");
		boolean result = orderDao.delete(id);
		if (!result) {
			throw new RuntimeException("order didn't be deleted");
		}
	}
}