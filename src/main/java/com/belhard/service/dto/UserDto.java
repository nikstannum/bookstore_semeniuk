package com.belhard.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private Long id;
	@NotBlank(message = "{general.errors.field.empty}")
	private String firstName;
	@NotBlank(message = "{general.errors.field.empty}")
	private String lastName;
	@NotBlank(message = "{general.errors.field.empty}")
	@Size(min = 3, message = "{errors.user.login.short}")
	private String email;
	@NotBlank(message = "{general.errors.field.empty}")
	@Size(min = 4, message = "{errors.user.password.short}")
	private String password;
	@NotNull(message = "{general.errors.field.empty}")
	private UserRoleDto userRoleDto;

	public enum UserRoleDto {
		ADMIN, MANAGER, USER
	}

}
