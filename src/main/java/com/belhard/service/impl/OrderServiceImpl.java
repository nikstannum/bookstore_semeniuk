package com.belhard.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.dao.BookRepository;
import com.belhard.dao.OrderInfoRepository;
import com.belhard.dao.OrderRepository;
import com.belhard.dao.entity.Book;
import com.belhard.dao.entity.Order;
import com.belhard.dao.entity.OrderInfo;
import com.belhard.exception.EntityNotFoundException;
import com.belhard.service.OrderService;
import com.belhard.service.dto.BookDto;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.OrderInfoDto;
import com.belhard.service.dto.UserDto;
import com.belhard.serviceutil.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final BookRepository bookRepository;
	private final OrderInfoRepository orderInfoRepository;
	private final Mapper mapper;
	private final MessageSource messageSource;

	@LogInvocation
	@Transactional
	@Override
	public OrderDto get(Long id) throws EntityNotFoundException {
		String message = messageSource.getMessage("order.error.not_found_by_id", new Object[] { id },
						LocaleContextHolder.getLocale());
		Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(message));
		return mapper.toDto(order);
	}

	@LogInvocation
	public OrderDto processCart(Map<Long, Integer> cart, UserDto userDto) {
		return createOrderDto(cart, userDto);
	}

	private OrderDto createOrderDto(Map<Long, Integer> cart, UserDto userDto) throws EntityNotFoundException {
		OrderDto orderDto = new OrderDto();
		orderDto.setUserDto(userDto);
		orderDto.setStatusDto(OrderDto.StatusDto.PENDING);
		List<OrderInfoDto> detailsDto = new ArrayList<>();

		cart.forEach((bookId, quantity) -> {
			OrderInfoDto orderInfoDto = new OrderInfoDto();
			Optional<Book> optionalBook = bookRepository.findById(bookId);
			String message = messageSource.getMessage("order.error.not_exist_with_id", new Object[] { bookId },
							LocaleContextHolder.getLocale());
			Book book = optionalBook.orElseThrow(() -> new EntityNotFoundException(message));
			BookDto bookDto = mapper.bookToDto(book);
			orderInfoDto.setBookDto(bookDto);
			orderInfoDto.setBookPrice(bookDto.getPrice());
			orderInfoDto.setBookQuantity(quantity);
			detailsDto.add(orderInfoDto);
		});
		BigDecimal totalCost = BigDecimal.ZERO;
		for (Long key : cart.keySet()) {
			String message = messageSource.getMessage("order.error.book_with_id_not_exists", new Object[] { key },
							LocaleContextHolder.getLocale());
			totalCost = totalCost
							.add(bookRepository.findById(key).orElseThrow(() -> new EntityNotFoundException(message))
											.getPrice().multiply(BigDecimal.valueOf(cart.get(key))));
		}
		orderDto.setTotalCost(totalCost);
		orderDto.setDetailsDto(detailsDto);
		return orderDto;
	}

	@LogInvocation
	@Override
	public List<OrderDto> getAll() {
		return orderRepository.findAll().stream().map(mapper::toDto).toList();
	}

	@LogInvocation
	@Transactional
	@Override
	public List<OrderDto> getAll(Paging paging) {
		int page = (int) paging.getPage();
		int limit = paging.getLimit();
		Sort sort = Sort.by(Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		Page<Order> ordersPage = orderRepository.findAll(pageable);
		return ordersPage.toList().stream().map(mapper::toDto).toList();
	}

	@LogInvocation
	@Override
	public long countAll() {
		return orderRepository.count();
	}

	@LogInvocation
	@Override
	public OrderDto create(OrderDto dto) {
		Order order = mapper.toEntity(dto);
		OrderDto orderDto = mapper.toDto(orderRepository.save(order));
		return orderDto;
	}

	@LogInvocation
	@Transactional
	@Override
	public OrderDto update(OrderDto dto) throws EntityNotFoundException {
		Order existing = orderRepository.findById(dto.getId()).orElseThrow(
						() -> new EntityNotFoundException(messageSource.getMessage("order.error.not_found_by_id",
										new Object[] { dto.getId() }, LocaleContextHolder.getLocale())));
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
		for (Long id : infoIdForDelete) {
			OrderInfo info = orderInfoRepository.findById(id)
							.orElseThrow(() -> new EntityNotFoundException(
											messageSource.getMessage("order.error.details.not_found_with_id",
															new Object[] { id }, LocaleContextHolder.getLocale())));
			info.setDeleted(true);
			orderInfoRepository.save(info);
		}
		Order order = mapper.toEntity(dto);
		Order updated = orderRepository.save(order);
		OrderDto orderDtoUpdated = mapper.toDto(updated);
		return orderDtoUpdated;
	}

	@LogInvocation
	@Override
	public OrderDto preProcessUpdate(OrderDto orderDto, List<OrderInfoDto> list, Long detailsDtoId,
					boolean increaseQuantity) throws EntityNotFoundException {
		List<Integer> listOfIndexOfSubjectToRemoval = new ArrayList<>();
		for (OrderInfoDto elm : list) {
			if (elm.getId() == detailsDtoId) {
				if (increaseQuantity) {
					elm.setBookQuantity(elm.getBookQuantity() + 1);
					Optional<Book> optionalBook = bookRepository.findById(elm.getBookDto().getId());
					Book book = optionalBook.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage(
									"order.error.book_with_id_not_found", new Object[] { elm.getBookDto().getId() },
									LocaleContextHolder.getLocale())));
					BigDecimal bookPriceFromCatalog = book.getPrice();
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

	@LogInvocation
	@Override
	public void delete(Long id) throws EntityNotFoundException {
		Order order = orderRepository.findById(id).orElseThrow(
						() -> new EntityNotFoundException(messageSource.getMessage("order.error.book_with_id_not_found",
										new Object[] { id }, LocaleContextHolder.getLocale())));
		order.setDeleted(true);
		orderRepository.save(order);
	}

	@Override
	public Page<OrderDto> getAll(Pageable pageable) { // FIXME
		// TODO Auto-generated method stub
		return null;
	}
}
