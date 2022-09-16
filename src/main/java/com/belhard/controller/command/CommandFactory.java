package com.belhard.controller.command;

import java.util.HashMap;
import java.util.Map;

import com.belhard.controller.command.impl.LoginCommand;
import com.belhard.controller.command.impl.LoginFormCommand;
import com.belhard.controller.command.impl.LogoutCommand;
import com.belhard.controller.command.impl.books.BookCommand;
import com.belhard.controller.command.impl.books.BooksCommand;
import com.belhard.controller.command.impl.books.CreateBookCommand;
import com.belhard.controller.command.impl.books.CreateBookFormCommand;
import com.belhard.controller.command.impl.books.UpdateBookCommand;
import com.belhard.controller.command.impl.books.UpdateBookFormCommand;
import com.belhard.controller.command.impl.errors.ErrorCommand;
import com.belhard.controller.command.impl.orders.AddToCart;
import com.belhard.controller.command.impl.orders.CartCommand;
import com.belhard.controller.command.impl.orders.CreateOrderCommand;
import com.belhard.controller.command.impl.orders.DecreaseQuantityCommand;
import com.belhard.controller.command.impl.orders.OrderCommand;
import com.belhard.controller.command.impl.orders.OrdersCommand;
import com.belhard.controller.command.impl.orders.UpdateOrderCommand;
import com.belhard.controller.command.impl.orders.UpdateOrderFormCommand;
import com.belhard.controller.command.impl.orders.IncreaseQuantityCommand;
import com.belhard.controller.command.impl.users.CreateUserCommand;
import com.belhard.controller.command.impl.users.CreateUserFormCommand;
import com.belhard.controller.command.impl.users.UpdateUserCommand;
import com.belhard.controller.command.impl.users.UpdateUserFormCommand;
import com.belhard.controller.command.impl.users.UserCommand;
import com.belhard.controller.command.impl.users.UsersCommand;
import com.belhard.controller.util.PagingUtil;
import com.belhard.service.BookService;
import com.belhard.service.OrderService;
import com.belhard.service.UserService;
import com.belhard.service.factory.ServiceFactory;

public class CommandFactory {
	private Map<String, Command> commands;
	private static final CommandFactory INSTANCE = new CommandFactory();

	private CommandFactory() {
		commands = new HashMap<>();
		// book
		commands.put("books", new BooksCommand(ServiceFactory.INSTANCE.getService(BookService.class), PagingUtil.INSTANCE));
		commands.put("book", new BookCommand(ServiceFactory.INSTANCE.getService(BookService.class)));
		commands.put("update_book_form", new UpdateBookFormCommand(ServiceFactory.INSTANCE.getService(BookService.class)));
		commands.put("create_book_form", new CreateBookFormCommand());
		commands.put("create_book", new CreateBookCommand(ServiceFactory.INSTANCE.getService(BookService.class)));
		commands.put("update_book", new UpdateBookCommand(ServiceFactory.INSTANCE.getService(BookService.class)));
		// user
		commands.put("users", new UsersCommand(ServiceFactory.INSTANCE.getService(UserService.class), PagingUtil.INSTANCE));
		commands.put("user", new UserCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
		commands.put("create_user_form", new CreateUserFormCommand());
		commands.put("create_user", new CreateUserCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
		commands.put("update_user_form", new UpdateUserFormCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
		commands.put("update_user", new UpdateUserCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
		// order
		commands.put("order", new OrderCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
		commands.put("orders", new OrdersCommand(ServiceFactory.INSTANCE.getService(OrderService.class), PagingUtil.INSTANCE));
		commands.put("checkout_order", new CreateOrderCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
		commands.put("update_order_form", new UpdateOrderFormCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
		commands.put("increase_quantity", new IncreaseQuantityCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
		commands.put("decrease_quantity", new DecreaseQuantityCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
		commands.put("update_order", new UpdateOrderCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
		// login
		commands.put("login_form", new LoginFormCommand());
		commands.put("login", new LoginCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
		commands.put("logout", new LogoutCommand());
		// other
		commands.put("add_to_cart", new AddToCart(ServiceFactory.INSTANCE.getService(BookService.class)));
		commands.put("cart", new CartCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
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
