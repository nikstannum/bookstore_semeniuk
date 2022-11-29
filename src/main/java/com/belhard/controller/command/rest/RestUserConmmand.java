package com.belhard.controller.command.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.belhard.aop.LogInvocation;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class RestUserConmmand {
	private final UserService service;

	@LogInvocation
	@ResponseBody
	@GetMapping()
	public Page<UserDto> allUsers(Pageable pageable) {
		Page<UserDto> users = service.getAll(pageable);
		return users;
	}
	
	@DeleteMapping("/{id}")
	@LogInvocation
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void blockUser(@PathVariable Long id) {
		service.delete(id);
	}

}
