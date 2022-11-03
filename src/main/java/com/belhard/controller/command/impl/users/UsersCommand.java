package com.belhard.controller.command.impl.users;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.PagingUtil;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.exception.MyAppException;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("users")
public class UsersCommand {

	private final UserService service;
	private final PagingUtil pagingUtil;
	private final MessageSource messageSource;

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
		model.addAttribute("message",
						messageSource.getMessage("general.result.of_search", null, LocaleContextHolder.getLocale()));
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
	public String updateUser(UserDto user, Model model) {
		UserDto updated = service.update(user);
		model.addAttribute("user", updated);
		model.addAttribute("message",
						messageSource.getMessage("user.update.success", null, LocaleContextHolder.getLocale()));
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
		model.addAttribute("message",
						messageSource.getMessage("user.create.success", null, LocaleContextHolder.getLocale()));
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
	public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session,
					Model model) {
		UserDto userDto = service.validate(email, password);
		session.setAttribute("user", userDto);
		model.addAttribute("message",
						messageSource.getMessage("user.login.success", null, LocaleContextHolder.getLocale()));
		return "index";
	}

	@RequestMapping("/logout")
	@LogInvocation
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}

	@ExceptionHandler
	public String myAppExc(MyAppException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "error";
	}
}
