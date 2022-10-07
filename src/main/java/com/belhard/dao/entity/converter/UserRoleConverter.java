package com.belhard.dao.entity.converter;

import javax.persistence.AttributeConverter;

import com.belhard.dao.entity.User.UserRole;

import lombok.extern.log4j.Log4j2;

@Log4j2
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
			log.error(attribute + " not supported");
			throw new IllegalArgumentException(attribute + " not supported");
		}
		}
	}

	@Override
	public UserRole convertToEntityAttribute(Long dbData) {
		if (dbData > Integer.MAX_VALUE) {
			log.error("value role_id from table role equals " + dbData);
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
			log.error(dbData + " not supported");
			throw new IllegalArgumentException(dbData + " not supported");
		}
		}
	}

}
