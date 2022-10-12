package com.belhard.dao.entity.converter;

import javax.persistence.AttributeConverter;

import com.belhard.dao.entity.Order.Status;

import lombok.extern.log4j.Log4j2;

@Log4j2
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
			log.error(attribute + " not supported");
			throw new IllegalArgumentException(attribute + " not supported");
		}
		}
	}

	@Override
	public Status convertToEntityAttribute(Long dbData) {
		if (dbData > Integer.MAX_VALUE) {
			log.error("value status_id from table books equals " + dbData);
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
			log.error(dbData + " not supported");
			throw new IllegalArgumentException(dbData + " not supported");
		}
		}
	}
}