package com.belhard.controller.command;

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

	public Command getCommand(String commandStr) {
		AnnotationConfigApplicationContext context = null;
		try {
			context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
			Command command;
			switch (commandStr) {
			// book
			case "books" -> command = context.getBean(BooksCommand.class);
			case "book" -> command = context.getBean(BookCommand.class);
			case "update_book_form" -> command = context.getBean(UpdateBookFormCommand.class);
			case "create_book_form" -> command = context.getBean(CreateBookFormCommand.class);
			case "create_book" -> command = context.getBean(CreateBookCommand.class);
			case "update_book" -> command = context.getBean(UpdateBookCommand.class);
			// user
			case "users" -> command = context.getBean(UsersCommand.class);
			case "user" -> command = context.getBean(UserCommand.class);
			case "create_user_form" -> command = context.getBean(CreateUserFormCommand.class);
			case "create_user" -> command = context.getBean(CreateUserCommand.class);
			case "update_user_form" -> command = context.getBean(UpdateUserFormCommand.class);
			case "update_user" -> command = context.getBean(UpdateUserCommand.class);
			// order
			case "order" -> command = context.getBean(OrderCommand.class);
			case "orders" -> command = context.getBean(OrdersCommand.class);
			case "checkout_order" -> command = context.getBean(CreateOrderCommand.class);
			case "update_order_form" -> command = context.getBean(UpdateOrderFormCommand.class);
			case "increase_quantity" -> command = context.getBean(IncreaseQuantityCommand.class);
			case "update_order" -> command = context.getBean(UpdateOrderCommand.class);
			// login
			case "login_form" -> command = context.getBean(LoginFormCommand.class);
			case "login" -> command = context.getBean(LoginCommand.class);
			case "logout" -> command = context.getBean(LogoutCommand.class);
			// other
			case "add_to_cart" -> command = context.getBean(AddToCart.class);
			case "cart" -> command = context.getBean(CartCommand.class);
			case "change_language" -> command = context.getBean(ChangeLanguageCommand.class);
			case "error" -> command = context.getBean(ErrorCommand.class);
			default -> command = context.getBean(ErrorCommand.class);
			}
			return command;
		} catch (BeansException e) {
			log.error(e);
		} finally {
//			context.close(); // FIXME 
		}
		throw new RuntimeException();
	}
}
