package com.belhard.dao.entity.converter;

import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

import com.belhard.dao.entity.Order.Status;

@Component
public class OrderStatusConverter implements AttributeConverter<Status, Long> {

	@Override
	public Long convertToDatabaseColumn(Status attribute) {
		switch (attribute) {
		case PENDING -> {
			return 1L;
		}
		case PAID -> {
			return 2L;
		}
		case DELIVERED -> {
			return 3L;
		}
		case CANCELED -> {
			return 4L;
		}
		default -> {
			throw new IllegalArgumentException(attribute + " not supported");
		}
		}
	}

	@Override
	public Status convertToEntityAttribute(Long dbData) {
		if (dbData > Integer.MAX_VALUE) {
			throw new RuntimeException(dbData + " not supported");
		}
		int intValue = dbData.intValue();
		switch (intValue) {
		case 1 -> {
			return Status.PENDING;
		}
		case 2 -> {

			return Status.PAID;
		}
		case 3 -> {
			return Status.DELIVERED;
		}
		case 4 -> {
			return Status.CANCELED;
		}
		default -> {
			throw new IllegalArgumentException(dbData + " not supported");
		}
		}
	}
}