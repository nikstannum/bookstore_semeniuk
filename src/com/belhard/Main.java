package com.belhard;

import com.belhard.controller.UserInteraction;
import com.belhard.dao.BookDao;
import com.belhard.dao.connection.DataSource;
import com.belhard.dao.impl.BookDaoImpl;
import com.belhard.service.BookService;
import com.belhard.service.impl.BookServiceImpl;

public class Main {

    public static void main(String[] args) {

        try (DataSource dataSource = new DataSource()) {
            BookDao bookDao = new BookDaoImpl(dataSource);
            BookService bookService = new BookServiceImpl(bookDao);
            UserInteraction userInteraction = new UserInteraction(bookService);
            userInteraction.userInteract();
        }
//        try (DataSource ds = new DataSource()) {
//            UserDao ud = new UserDaoImpl(ds);
//            UserServiceImpl us = new UserServiceImpl(ud);
//            System.out.println(us.validate("electricity@gmail.com", "induction"));
//        }
    }
}
