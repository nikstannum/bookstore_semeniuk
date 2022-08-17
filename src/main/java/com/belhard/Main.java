package com.belhard;

import com.belhard.controller.UserInteraction;

public class Main {

    public static void main(String[] args) {

//        try (DataSource dataSource = new DataSource()) {
//            BookDao bookDao = new BookDaoImpl(dataSource);
//            BookService bookService = new BookServiceImpl(bookDao);
//            UserInteraction userInteraction = new UserInteraction(bookService);
//            userInteraction.userInteractForBook();
//        }
//
//        try (DataSource dataSource = new DataSource()) {
//            UserDao userDao = new UserDaoImpl(dataSource);
//            UserService userService = new UserServiceImpl(userDao);
//            UserInteraction userInteraction = new UserInteraction(userService);
//            userInteraction.userInteractForUser();
//        }
        UserInteraction.userInteract();
    }
}
