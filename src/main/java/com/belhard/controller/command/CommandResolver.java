package com.belhard.controller.command;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.belhard.ContextConfiguration;
import com.belhard.controller.command.impl.ChangeLanguageCommand;
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
import com.belhard.controller.command.impl.orders.IncreaseQuantityCommand;
import com.belhard.controller.command.impl.orders.OrderCommand;
import com.belhard.controller.command.impl.orders.OrdersCommand;
import com.belhard.controller.command.impl.orders.UpdateOrderCommand;
import com.belhard.controller.command.impl.orders.UpdateOrderFormCommand;
import com.belhard.controller.command.impl.users.CreateUserCommand;
import com.belhard.controller.command.impl.users.CreateUserFormCommand;
import com.belhard.controller.command.impl.users.UpdateUserCommand;
import com.belhard.controller.command.impl.users.UpdateUserFormCommand;
import com.belhard.controller.command.impl.users.UserCommand;
import com.belhard.controller.command.impl.users.UsersCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class CommandResolver {


	private final Map<String, Class<? extends Command>> commands;

	private CommandResolver() {
		commands = new HashMap<>();

		// book
		commands.put("books", BooksCommand.class);
		commands.put("book", BookCommand.class);
		commands.put("update_book_form", UpdateBookFormCommand.class);
		commands.put("create_book_form", CreateBookFormCommand.class);
		commands.put("create_book", CreateBookCommand.class);
		commands.put("update_book", UpdateBookCommand.class);
		// user
		commands.put("users", UsersCommand.class);
		commands.put("user", UserCommand.class);
		commands.put("create_user_form", CreateUserFormCommand.class);
		commands.put("create_user", CreateUserCommand.class);
		commands.put("update_user_form", UpdateUserFormCommand.class);
		commands.put("update_user", UpdateUserCommand.class);
		// order
		commands.put("order", OrderCommand.class);
		commands.put("orders", OrdersCommand.class);
		commands.put("checkout_order", CreateOrderCommand.class);
		commands.put("update_order_form", UpdateOrderFormCommand.class);
		commands.put("increase_quantity", IncreaseQuantityCommand.class);
		commands.put("decrease_quantity", DecreaseQuantityCommand.class);
		commands.put("update_order", UpdateOrderCommand.class);
		commands.put("add_to_cart", AddToCart.class);
		commands.put("cart", CartCommand.class);
		// login
		commands.put("login_form", LoginFormCommand.class);
		commands.put("login", LoginCommand.class);
		commands.put("logout", LogoutCommand.class);
		// other
		commands.put("change_language", ChangeLanguageCommand.class);
		commands.put("error", ErrorCommand.class);
	}

	public Class<? extends Command> getCommand(String command) {
		Class<? extends Command> commandInstance = commands.get(command);
		if (commandInstance == null) {
			commandInstance = commands.get("error");
		}
		return commandInstance;
	}

}
