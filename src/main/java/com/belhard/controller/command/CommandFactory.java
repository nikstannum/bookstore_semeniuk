package com.belhard.controller.command;

import java.util.HashMap;
import java.util.Map;

import com.belhard.controller.command.impl.books.BookCommand;
import com.belhard.controller.command.impl.books.BooksCommand;
import com.belhard.controller.command.impl.books.CreateBookCommand;
import com.belhard.controller.command.impl.books.CreateBookFormCommand;
import com.belhard.controller.command.impl.books.UpdateBookCommand;
import com.belhard.controller.command.impl.books.UpdateBookFormCommand;
import com.belhard.controller.command.impl.errors.ErrorCommand;
import com.belhard.controller.command.impl.orders.OrderCommand;
import com.belhard.controller.command.impl.orders.OrdersCommand;
import com.belhard.controller.command.impl.users.CreateUserCommand;
import com.belhard.controller.command.impl.users.CreateUserFormCommand;
import com.belhard.controller.command.impl.users.UpdateUserCommand;
import com.belhard.controller.command.impl.users.UpdateUserFormCommand;
import com.belhard.controller.command.impl.users.UserCommand;
import com.belhard.controller.command.impl.users.UsersCommand;
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
		commands.put("update_user_form", new UpdateUserFormCommand());
		commands.put("update_user", new UpdateUserCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
		commands.put("create_book_form", new CreateBookFormCommand());
		commands.put("create_book", new CreateBookCommand(ServiceFactory.INSTANCE.getService(BookService.class)));
		commands.put("update_book_form", new UpdateBookFormCommand());
		commands.put("update_book", new UpdateBookCommand(ServiceFactory.INSTANCE.getService(BookService.class)));
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