package com.belhard.service.dto.util;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.belhard.service.dto.UserDto.UserRoleDto;

@Component
public class CheckFieldUserRoleDto implements ConstraintValidator<ConstraintField, UserRoleDto> {

	@Override
	public boolean isValid(UserRoleDto userRoleDto, ConstraintValidatorContext context) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
						.getAuthorities();
		if (authorities.contains("ADMIN") && userRoleDto == null) {
			return false;
		}
		return true;
	}

}
