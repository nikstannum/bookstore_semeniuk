package main.java.com.belhard;

import java.math.BigDecimal;
import main.java.com.belhard.controller.UserInteraction;
import main.java.com.belhard.dao.BookDao;
import main.java.com.belhard.dao.UserDao;
import main.java.com.belhard.dao.connection.DataSource;
import main.java.com.belhard.dao.impl.BookDaoImpl;
import main.java.com.belhard.dao.impl.UserDaoImpl;
import main.java.com.belhard.service.BookService;
import main.java.com.belhard.service.impl.BookServiceImpl;
import main.java.com.belhard.service.impl.UserServiceImpl;

public class Main {

    public static void main(String[] args) {

        try (DataSource dataSource = new DataSource()) {
            BookDao bookDao = new BookDaoImpl(dataSource);
            BookService bookService = new BookServiceImpl(bookDao);
            UserInteraction userInteraction = new UserInteraction(bookService);
            userInteraction.userInteract();

            UserDao ud = new UserDaoImpl(dataSource);
            UserServiceImpl us = new UserServiceImpl(ud);
            System.out.println(us.validate("electricity@gmail.com", "induction"));

            BigDecimal cost = bookService.totalCostAllBooksOfAuthor("Daniel Defoe");
            System.out.println(cost);

            int count = bookService.countAllBooks();
            System.out.println(count);
        }
    }
}
