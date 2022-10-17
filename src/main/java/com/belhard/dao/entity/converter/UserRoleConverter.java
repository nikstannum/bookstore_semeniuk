package com.belhard.dao.entity.converter;

import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

import com.belhard.dao.entity.User.UserRole;

@Component
public class UserRoleConverter implements AttributeConverter<UserRole, Long> {

	@Override
	public Long convertToDatabaseColumn(UserRole attribute) {
		switch (attribute) {
		case ADMIN -> {
			return 1L;
		}
		case MANAGER -> {
			return 2L;
		}
		case USER -> {
			return 3L;
		}
		default -> {
			throw new IllegalArgumentException(attribute + " not supported");
		}
		}
	}

	@Override
	public UserRole convertToEntityAttribute(Long dbData) {
		if (dbData > Integer.MAX_VALUE) {
			throw new RuntimeException(dbData + " not supported");
		}
		int intValue = dbData.intValue();
		switch (intValue) {
		case 1 -> {
			return UserRole.ADMIN;
		}
		case 2 -> {

			return UserRole.MANAGER;
		}
		case 3 -> {
			return UserRole.USER;
		}
		default -> {
			throw new IllegalArgumentException(dbData + " not supported");
		}
		}
	}

}
