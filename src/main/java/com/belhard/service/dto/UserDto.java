package com.belhard.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private Long id;
	private String firstName;
	private String lastName;
	@NotBlank(message = "{errors.user.login.empty}")
	@Size(min = 6, message = "{errors.user.login.short}")
	private String email;
	@NotBlank(message = "{errors.user.password.empty}")
	@Size(min = 6, message = "{errors.user.password.short}")
	private String password;
	private UserRoleDto userRoleDto;

	public enum UserRoleDto {
		ADMIN, MANAGER, USER
	}

}
