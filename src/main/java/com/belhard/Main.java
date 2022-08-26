package com.belhard;

import com.belhard.controller.UserServletForServer;
import com.belhard.dao.BookDao;
import com.belhard.dao.UserDao;
import com.belhard.dao.connection.DataSource;
import com.belhard.dao.impl.BookDaoImpl;
import com.belhard.dao.impl.UserDaoImpl;
import com.belhard.server.Servlet;
import com.belhard.server.Server;
import com.belhard.service.BookService;
import com.belhard.service.UserService;
import com.belhard.service.impl.BookServiceImpl;
import com.belhard.service.impl.UserServiceImpl;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        DataSource dataSource = DataSource.INSTANCE;
        BookDao bookDao = new BookDaoImpl(dataSource);
        UserDao userDao = new UserDaoImpl(dataSource);

        BookService bookService = new BookServiceImpl(bookDao);
        UserService userService = new UserServiceImpl(userDao);

        Servlet servlet = new UserServletForServer(bookService, userService);
        Server server = new Server(servlet);
        server.run();
    }
}