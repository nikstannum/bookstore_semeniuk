package com.belhard.serviceutil;

import java.util.ArrayList;
import java.util.List;

import com.belhard.dao.entity.Book;
import com.belhard.dao.entity.Book.BookCover;
import com.belhard.dao.entity.OrderInfo;
import com.belhard.dao.entity.User;
import com.belhard.dao.entity.User.UserRole;
import com.belhard.service.dto.BookDto;
import com.belhard.service.dto.BookDto.BookCoverDto;
import com.belhard.service.dto.OrderInfoDto;
import com.belhard.service.dto.UserDto;
import com.belhard.service.dto.UserDto.UserRoleDto;

public enum Mapper {
	INSTANCE;

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
		user.setUserRole(UserRole.valueOf(userDto.getUserRoleDto().toString()));
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
			entity.setOrderId(orderDtoId);
			infosEntity.add(entity);
		}
		return infosEntity;
	}

	public List<OrderInfoDto> infosToDto(List<OrderInfo> entity) {
		List<OrderInfoDto> infosDto = new ArrayList<>();
		for (OrderInfo elm : entity) {
			OrderInfoDto dto = new OrderInfoDto();
			dto.setBookDto(bookToDto(elm.getBook()));
			dto.setBookPrice(elm.getBookPrice());
			dto.setBookQuantity(elm.getBookQuantity());
			dto.setOrderDtoId(elm.getOrderId());
			dto.setId(elm.getId());
			infosDto.add(dto);
		}
		return infosDto;

	}

}
