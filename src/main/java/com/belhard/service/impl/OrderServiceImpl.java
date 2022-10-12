package com.belhard.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.dao.BookRepository;
import com.belhard.dao.OrderRepository;
import com.belhard.dao.entity.Order;
import com.belhard.dao.entity.OrderInfo;
import com.belhard.service.OrderService;
import com.belhard.service.dto.BookDto;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.OrderInfoDto;
import com.belhard.service.dto.UserDto;
import com.belhard.serviceutil.Mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final BookRepository bookRepository;
	private final Mapper mapper;

	@Transactional
	@Override
	public OrderDto get(Long id) {
		Order order = orderRepository.get(id);
		if (order == null) {
			throw new RuntimeException("Couldn't find order with id: " + id);
		}
		return mapper.toDto(order);
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
			BookDto bookDto = mapper.bookToDto(bookRepository.get(bookId));
			orderInfoDto.setBookDto(bookDto);
			orderInfoDto.setBookPrice(bookDto.getPrice());
			orderInfoDto.setBookQuantity(quantity);
			detailsDto.add(orderInfoDto);
		});
		BigDecimal totalCost = BigDecimal.ZERO;
		for (Long key : cart.keySet()) {
			totalCost = totalCost.add(bookRepository.get(key).getPrice().multiply(BigDecimal.valueOf(cart.get(key))));
		}
		orderDto.setTotalCost(totalCost);
		orderDto.setDetailsDto(detailsDto);
		return orderDto;
	}

	@Override
	public List<OrderDto> getAll() {
		log.debug("Service method called successfully");
		return orderRepository.getAll().stream().map(mapper::toDto).toList();
	}

	@Transactional
	@Override
	public List<OrderDto> getAll(Paging paging) {
		int limit = paging.getLimit();
		long offset = paging.getOffset();
		log.debug("Service method called successfully");
		return orderRepository.getAll(limit, offset).stream().map(mapper::toDto).toList();
	}

	@Override
	public long countAll() {
		log.debug("Service method called successfully");
		return orderRepository.countAll();
	}

	@Override
	public OrderDto create(OrderDto dto) {
		log.debug("Service method called successfully");
		Order order = mapper.toEntity(dto);
		OrderDto orderDto = mapper.toDto(orderRepository.create(order));
		return orderDto;
	}

	@Transactional
	@Override
	public OrderDto update(OrderDto dto) {
		log.debug("Service method called successfully");
		Order existing = orderRepository.get(dto.getId());
		if (existing == null) {
			throw new RuntimeException("order wasn't found");
		}
		List<OrderInfo> infosFromDB = existing.getDetails();
		List<Long> listInfoIdFromDB = infosFromDB.stream().map(elm -> elm.getId()).toList();
		List<OrderInfo> infos = mapper.toDetails(dto.getDetailsDto());
		List<Long> listInfoIdFromRequest = infos.stream().map(elm -> elm.getId()).toList();
		List<Long> infoIdForDelete = new ArrayList<>();
		for (Long elm : listInfoIdFromDB) {
			if (!listInfoIdFromRequest.contains(elm)) {
				infoIdForDelete.add(elm);
			}
		}
		orderRepository.removeRedundantDetails(infoIdForDelete);
		Order order = mapper.toEntity(dto);
		Order updated = orderRepository.update(order);
		OrderDto orderDtoUpdated = mapper.toDto(updated);
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
					BigDecimal bookPriceFromCatalog = bookRepository.get(elm.getBookDto().getId()).getPrice();
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
		boolean result = orderRepository.delete(id);
		if (!result) {
			throw new RuntimeException("order didn't be deleted");
		}
	}
}