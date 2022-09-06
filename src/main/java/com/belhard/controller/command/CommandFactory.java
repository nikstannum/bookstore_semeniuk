package com.belhard.controller.command;

import java.util.HashMap;
import java.util.Map;

import com.belhard.controller.command.impl.BookCommand;
import com.belhard.controller.command.impl.BooksCommand;
import com.belhard.controller.command.impl.CreateUserCommand;
import com.belhard.controller.command.impl.CreateUserFormCommand;
import com.belhard.controller.command.impl.ErrorCommand;
import com.belhard.controller.command.impl.OrderCommand;
import com.belhard.controller.command.impl.OrdersCommand;
import com.belhard.controller.command.impl.UserCommand;
import com.belhard.controller.command.impl.UsersCommand;
import com.belhard.service.BookService;
import com.belhard.service.OrderService;
import com.belhard.service.UserService;
import com.belhard.service.factory.ServiceFactory;

public class CommandFactory {
	private Map<String, Command> commands;
	private static final CommandFactory INSTANCE = new CommandFactory();

	private CommandFactory() {
		commands = new HashMap<>();
		commands.put("books", new BooksCommand(ServiceFactory.INSTANCE.getService(BookService.class)));
		commands.put("book", new BookCommand(ServiceFactory.INSTANCE.getService(BookService.class)));
		commands.put("users", new UsersCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
		commands.put("user", new UserCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
		commands.put("order", new OrderCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
		commands.put("orders", new OrdersCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
		commands.put("create_user_form", new CreateUserFormCommand());
		commands.put("create_user", new CreateUserCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
		commands.put("error", new ErrorCommand());

	}

	public Command getCommand(String command) {
		Command commandInstance = commands.get(command);
		if (commandInstance == null) {
			commandInstance = commands.get("error");
		}
		return commandInstance;
	}

	public static CommandFactory getINSTANCE() {
		return INSTANCE;
	}
}
