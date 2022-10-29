package com.belhard.controller.command.impl.users;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.PagingUtil;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

@Controller
@RequestMapping("users")
public class UsersCommand {

	private final UserService service;
	private final PagingUtil pagingUtil;

	public UsersCommand(UserService service, PagingUtil pagingUtil) {
		this.service = service;
		this.pagingUtil = pagingUtil;
	}

	@LogInvocation
	@RequestMapping("/all")
	public String allUsers(@RequestParam(defaultValue = "10") Integer limit,
					@RequestParam(defaultValue = "1") Long page, Model model) {
		Paging paging = pagingUtil.getPaging(limit, page);
		List<UserDto> users = service.getAll(paging);
		long totalEntities = service.countAll();
		long totalPages = pagingUtil.getTotalPages(totalEntities, paging.getLimit());
		model.addAttribute("users", users);
		model.addAttribute("currentCommand", "users");
		model.addAttribute("currentPage", paging.getPage());
		model.addAttribute("totalPages", totalPages);
		return "user/users";
	}

	@RequestMapping("/{id}")
	@LogInvocation
	public String userById(@PathVariable Long id, Model model) {
		UserDto dto = service.get(id);
		model.addAttribute("user", dto);
		model.addAttribute("message", "result of search:");
		return "user/user";
	}

	@LogInvocation
	@RequestMapping("/update")
	public String updateUserForm(Model model, @RequestParam Long id) {
		UserDto user = service.get(id);
		model.addAttribute("user", user);
		return "user/updateUserForm";
	}

	@RequestMapping("/update_user")
	@LogInvocation
	public String updateUser(@ModelAttribute UserDto dto, Model model) {
		UserDto updated = service.update(dto);
		model.addAttribute("user", updated);
		model.addAttribute("message", "user updated successfully");
		return "user/user";
	}

	@RequestMapping("/create_user_form")
	@LogInvocation
	public String createUserForm() {
		return "user/createUserForm";
	}

	@ModelAttribute
	public UserDto user() {
		UserDto user = new UserDto();
		return user;
	}

	@RequestMapping("/create_user")
	@LogInvocation
	public String createUser(@ModelAttribute @Valid UserDto user, Errors errors, Model model, HttpSession session) {
		if (errors.hasErrors()) {
			model.addAttribute("errors", errors.getFieldErrors());
			return "user/createUserForm";
		}
		UserDto created = service.create(user);
		model.addAttribute("message", "User created successfully");
		session.setAttribute("user", created);
		return "user/user";
	}

	@RequestMapping("/login_form")
	@LogInvocation
	public String loginForm() {
		return "user/loginForm";
	}

	@LogInvocation
	@RequestMapping("/login")
	public String loginUser(@RequestParam String email, @RequestParam String password, HttpServletRequest req) {
		UserDto userDto = service.validate(email, password);
		HttpSession session = req.getSession();
		session.setAttribute("user", userDto);
		req.setAttribute("message", "successfully login");
		return "index";
	}

	@RequestMapping("/logout")
	@LogInvocation
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
}
