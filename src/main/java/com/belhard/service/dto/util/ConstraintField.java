package com.belhard.service.dto.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.stereotype.Component;

@Component
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckFieldUserRoleDto.class)
public @interface ConstraintField {

	String message() default "{general.errors.field.empty}";

	Class<?>[] groups() default {}; // FIXME try delete

	Class<? extends Payload>[] payload() default {}; // FIXME try delete

}
