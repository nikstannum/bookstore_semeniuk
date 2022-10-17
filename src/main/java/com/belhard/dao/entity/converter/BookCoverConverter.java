package com.belhard.dao.entity.converter;

import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

import com.belhard.dao.entity.Book.BookCover;

@Component
public class BookCoverConverter implements AttributeConverter<BookCover, Long> {

	@Override
	public Long convertToDatabaseColumn(BookCover attribute) {
		switch (attribute) {
		case SOFT -> {
			return 1L;
		}
		case HARD -> {
			return 2L;
		}
		case SPECIAL -> {
			return 3L;
		}
		default -> {
			throw new IllegalArgumentException(attribute + " not supported");
		}
		}
	}

	@Override
	public BookCover convertToEntityAttribute(Long dbData) {
		if (dbData > Integer.MAX_VALUE) {
			throw new RuntimeException(dbData + " not supported");
		}
		int intValue = dbData.intValue();
		switch (intValue) {
		case 1 -> {
			return BookCover.SOFT;
		}
		case 2 -> {

			return BookCover.HARD;
		}
		case 3 -> {
			return BookCover.SPECIAL;
		}
		default -> {
			throw new IllegalArgumentException(dbData + " not supported");
		}
		}
	}
}
