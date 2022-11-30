package com.belhard.serviceutil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.belhard.dao.entity.Book;
import com.belhard.dao.entity.Book.BookCover;
import com.belhard.dao.entity.Order;
import com.belhard.dao.entity.OrderInfo;
import com.belhard.dao.entity.User;
import com.belhard.dao.entity.User.UserRole;
import com.belhard.service.dto.BookDto;
import com.belhard.service.dto.BookDto.BookCoverDto;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.OrderInfoDto;
import com.belhard.service.dto.UserDto;
import com.belhard.service.dto.UserDto.UserRoleDto;

@Component
public class Mapper {

	public UserDto userToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setUserRoleDto(UserRoleDto.valueOf(user.getRole().toString()));
		return userDto;
	}

	public User userToEntity(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		if (userDto.getUserRoleDto() == null) {
			user.setUserRole(UserRole.USER);
		} else {
			user.setUserRole(UserRole.valueOf(userDto.getUserRoleDto().toString()));
		}
		return user;
	}

	public Book bookToEntity(BookDto bookDto) {
		Book book = new Book();
		book.setId(bookDto.getId());
		book.setTitle(bookDto.getTitle());
		book.setAuthor(bookDto.getAuthor());
		book.setIsbn(bookDto.getIsbn());
		book.setPages(bookDto.getPages());
		book.setPrice(bookDto.getPrice());
		book.setCover(BookCover.valueOf(bookDto.getCoverDto().toString()));
		return book;
	}

	public BookDto bookToDto(Book book) {
		BookDto bookDto = new BookDto();
		bookDto.setId(book.getId());
		bookDto.setTitle(book.getTitle());
		bookDto.setAuthor(book.getAuthor());
		bookDto.setIsbn(book.getIsbn());
		bookDto.setPages(book.getPages());
		bookDto.setPrice(book.getPrice());
		bookDto.setCoverDto(BookCoverDto.valueOf(book.getCover().toString()));
		return bookDto;
	}

	public List<OrderInfo> infosToEntity(List<OrderInfoDto> infosDto, Long orderDtoId) {
		List<OrderInfo> infosEntity = new ArrayList<>();
		for (OrderInfoDto elm : infosDto) {
			OrderInfo entity = new OrderInfo();
			entity.setId(orderDtoId);
			entity.setBook(bookToEntity(elm.getBookDto()));
			entity.setBookPrice(elm.getBookPrice());
			entity.setBookQuantity(elm.getBookQuantity());
			entity.setOrder(toEntity(elm.getOrderDto()));
			infosEntity.add(entity);
		}
		return infosEntity;
	}

	public OrderDto toDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setStatusDto(OrderDto.StatusDto.valueOf(order.getStatus().toString()));
		orderDto.setUserDto(userToDto(order.getUser()));
		orderDto.setTotalCost(order.getTotalCost());
		orderDto.setDetailsDto(toDetailsDto(order.getDetails()));
		for (OrderInfoDto elm : orderDto.getDetailsDto()) {
			elm.setOrderDto(orderDto);
		}
		return orderDto;
	}

	public List<OrderInfoDto> toDetailsDto(List<OrderInfo> details) {
		List<OrderInfoDto> detailsDto = new ArrayList<>();
		for (OrderInfo elm : details) {
			OrderInfoDto dto = new OrderInfoDto();
			dto.setId(elm.getId());
			dto.setBookDto(bookToDto(elm.getBook()));
			dto.setBookQuantity(elm.getBookQuantity());
			dto.setBookPrice(elm.getBookPrice());
			detailsDto.add(dto);
		}
		return detailsDto;
	}

	public List<OrderInfo> toDetails(List<OrderInfoDto> detailsDto) {
		List<OrderInfo> details = new ArrayList<>(detailsDto.size());
		for (OrderInfoDto elm : detailsDto) {
			OrderInfo entity = new OrderInfo();
			entity.setId(elm.getId());
			entity.setBook(bookToEntity(elm.getBookDto()));
			entity.setBookQuantity(elm.getBookQuantity());
			entity.setBookPrice(elm.getBookPrice());
			details.add(entity);
		}
		return details;
	}

	public Order toEntity(OrderDto dto) {
		Order order = new Order();
		order.setId(dto.getId());
		order.setUser(userToEntity(dto.getUserDto()));
		order.setStatus(Order.Status.valueOf(dto.getStatusDto().toString()));
		BigDecimal totalCost = dto.getTotalCost();
		dto.getDetailsDto().stream().map(elm -> elm.getBookPrice().multiply(BigDecimal.valueOf(elm.getBookQuantity())))
						.forEach(totalCost::add);
		order.setTotalCost(totalCost);
		order.setDetails(toDetails(dto.getDetailsDto()));
		for (OrderInfo elm : order.getDetails()) {
			elm.setOrder(order);
		}
		return order;
	}
}
