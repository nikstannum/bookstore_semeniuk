package com.belhard.controller.command.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@GetMapping("/all/data")
	public List<UserDto> allUsers() {
		List<UserDto> users = service.getAll();
		return users;
	}


}
