package com.belhard.controller.command.impl.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.belhard.aop.LogInvocation;
import com.belhard.aop.UpdateAuthorities;
import com.belhard.exception.MyAppException;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("users")
public class UsersCommand {

	private final UserService userService;
	private final MessageSource messageSource;

//	@LogInvocation
//	@GetMapping("/all")
//	public String allUsers(@RequestParam(defaultValue = "10") Integer limit,
//					@RequestParam(defaultValue = "1") Long page, Model model) {
//		Paging paging = pagingUtil.getPaging(limit, page);
//		List<UserDto> users = userService.getAll(paging);
//		long totalEntities = userService.countAll();
//		long totalPages = pagingUtil.getTotalPages(totalEntities, paging.getLimit());
//		model.addAttribute("users", users);
//		model.addAttribute("currentCommand", "users");
//		model.addAttribute("currentPage", paging.getPage());
//		model.addAttribute("totalPages", totalPages);
//		return "user/users";
//	}

	@LogInvocation
	@GetMapping("/all")
	public String allUsers() {
		return "user/users_js";
	}

	@GetMapping("/{id}")
	@LogInvocation
	public String userById(@PathVariable Long id, Model model) {
		UserDto dto = userService.get(id);
		model.addAttribute("user", dto);
		model.addAttribute("message",
						messageSource.getMessage("general.result.of_search", null, LocaleContextHolder.getLocale()));
		return "user/user";
	}

	@LogInvocation
	@GetMapping("/update")
	public String updateUserForm(Model model, @RequestParam Long id) {
		UserDto user = userService.get(id);
		model.addAttribute("user", user);
		return "user/updateUserForm";
	}

	@PostMapping("/update_user")
	@LogInvocation
	@UpdateAuthorities
	public String updateUser(UserDto user, HttpServletRequest req, Model model) {
		UserDto updated = userService.update(user);
		model.addAttribute("user", updated);
		model.addAttribute("message",
						messageSource.getMessage("user.update.success", null, LocaleContextHolder.getLocale()));
		return "user/user";
	}

	@GetMapping("/create_user_form")
	@LogInvocation
	public String createUserForm() {
		return "user/createUserForm";
	}

	@ModelAttribute
	public UserDto user() {
		UserDto user = new UserDto();
		return user;
	}

	@PostMapping("/create_user")
	@LogInvocation
	public String createUser(@ModelAttribute @Valid UserDto user, Errors errors, Model model, HttpSession session) {
		if (errors.hasErrors()) {
			model.addAttribute("errors", errors.getFieldErrors());
			return "user/createUserForm";
		}
		UserDto created = userService.create(user);
		model.addAttribute("message",
						messageSource.getMessage("user.create.success", null, LocaleContextHolder.getLocale()));
		session.setAttribute("user", created);
		return "user/user";
	}
//	@GetMapping("/login_form")
//	@LogInvocation
//	public String loginForm() {
//		return "user/loginForm";
//	}

	@PostMapping("/login")
	@LogInvocation
	public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session,
					Model model) {
		UserDto userDto = userService.validate(email, password);
		session.setAttribute("user", userDto);
		model.addAttribute("message",
						messageSource.getMessage("user.login.success", null, LocaleContextHolder.getLocale()));
		return "index";
	}

	@GetMapping("/personal")
	@LogInvocation
	public String personalData() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDto userDto = userService.getUserByEmail(email);
		Long id = userDto.getId();
		return "redirect:/users/" + id;
	}

//	@PostMapping("/logout")
//	@LogInvocation
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "index";
//	}

	@ExceptionHandler
	public String myAppExc(MyAppException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "error";
	}
}
