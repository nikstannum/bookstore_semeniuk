package com.belhard.controller;

import com.belhard.dao.BookDao;
import com.belhard.dao.UserDao;
import com.belhard.dao.connection.DataSource;
import com.belhard.dao.impl.BookDaoImpl;
import com.belhard.dao.impl.UserDaoImpl;
import com.belhard.service.BookService;
import com.belhard.service.UserService;
import com.belhard.service.dto.BookDto;
import com.belhard.service.dto.BookDto.BookCoverDto;
import com.belhard.service.dto.UserDto;
import com.belhard.service.dto.UserDto.UserRoleDto;
import com.belhard.service.impl.BookServiceImpl;
import com.belhard.service.impl.UserServiceImpl;
import java.math.BigDecimal;
import java.util.Scanner;

public class UserInteraction {
    private static final String MESSAGE1_FOR_BOOK = """
            If you want:
            \t1) to view all books, please, insert 'all' and press ENTER
            \t2) to exit from app, please, insert 'exit' and press ENTER: 
            """;
    private static final String MESSAGE2_FOR_BOOK = """
            If you want:
            \t1) to view detailed information about the book or to delete any book from list or update any book, please, insert book's id and press ENTER
            \t2) to create and insert new book into list, please, insert 'create' and press ENTER
            \t3) to exit from app, please, insert 'exit' and press ENTER: 
            """;
    private static final String MESSAGE3_FOR_BOOK = """
            If you want to view detail information about the book insert 'v' and press ENTER
            if you want to update the book insert 'u' and press ENTER
            if you want to delete the book insert 'd' and press ENTER: 
            """;

    private static final String messageForTitle = "insert title of book: ";//FIXME SNAKE_CASE for const names
    private static final String messageForAuthor = "insert author of book: ";
    private static final String messageForIsbn = "insert isbn of book: ";
    private static final String messageForPages = "insert number of pages: ";
    private static final String messageForPrice = "insert price : ";
    private static final String messageForCover = "insert cover of book : ";


    private static final String MESSAGE1_FOR_USER = """
            If you want:
            \t1) to view all users, please, insert 'all' and press ENTER
            \t2) to exit from app, please, insert 'exit' and press ENTER: 
            """;
    private static final String MESSAGE2_FOR_USER = """
            If you want:
            \t1) to view detailed information about the user or to delete any user from list or update any user, please, insert user's id and press ENTER
            \t2) to create and insert new book into list, please, insert 'create' and press ENTER
            \t3) to exit from app, please, insert 'exit' and press ENTER: 
            """;
    private static final String MESSAGE3_FOR_USER = """
            If you want to view detail information about the user insert 'v' and press ENTER
            if you want to update the user insert 'u' and press ENTER
            if you want to delete the user insert 'd' and press ENTER: 
            """;

    private static final String messageForFirstName = "insert first name: ";
    private static final String messageForLastName = "insert last name: ";
    private static final String messageForEmail = "insert email: ";
    private static final String messageForPassword = "insert password: ";
    private static final String messageForRole = "insert role : ";

    private BookService bookService;
    private UserService userService;

    public UserInteraction(BookService bookService) {
        this.bookService = bookService;
    }

    public UserInteraction(UserService userService) {
        this.userService = userService;
    }

    public static void userInteract() {
        System.out.print("If you are interested in working with books, insert 'books:\nIf you are interested in working with users, insert 'users:");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        switch (answer) {
            case "books" -> {
                try (DataSource dataSource = new DataSource()) {
                    BookDao bookDao = new BookDaoImpl(dataSource);
                    BookService bookService = new BookServiceImpl(bookDao);
                    UserInteraction userInteraction = new UserInteraction(bookService);
                    userInteraction.userInteractForBook();
                }
            }
            case "users" -> {
                try (DataSource dataSource = new DataSource()) {
                    UserDao userDao = new UserDaoImpl(dataSource);
                    UserService userService = new UserServiceImpl(userDao);
                    UserInteraction userInteraction = new UserInteraction(userService);
                    userInteraction.userInteractForUser();
                }
            }
            default -> {
                throw new RuntimeException();
            }
        }
    }

    public void userInteractForBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(MESSAGE1_FOR_BOOK);
        String result = scanner.nextLine();
        if (result.equalsIgnoreCase("all")) {
            bookService.getAll().forEach(b -> System.out.printf
                    ("Book: id = %d, title = %s, author = %s\n", b.getId(), b.getTitle(), b.getAuthor()));
        } else if (result.equalsIgnoreCase("exit")) {
            System.out.println("Good bye");
            return;
        } else {
            throw new RuntimeException("you entered an invalid value");
        }
        System.out.print(MESSAGE2_FOR_BOOK);
        String resultHelp = scanner.nextLine();
        try {
            long id = Long.parseLong(resultHelp);
            System.out.print(MESSAGE3_FOR_BOOK);
            String resultAction = scanner.nextLine();
            if (resultAction.toLowerCase().equals("v")) {//FIXME equalsIgnoreCase
                System.out.println(bookService.getById(id));
            } else if (resultAction.toLowerCase().equals("d")) {
                bookService.delete(id);
            } else if (resultAction.toLowerCase().equals("u")) {
                BookDto bookDto = createBookDto(scanner);
                bookDto.setId(id);
                bookService.update(bookDto);
            } else {
                throw new RuntimeException("you entered an invalid value");
            }
        } catch (NumberFormatException e) {
            switch (resultHelp.toLowerCase()) {//FIXME tye to be consistent - use either SWITCH or IF-ELSE-IF
                case "create" -> bookService.create(createBookDto(scanner));
                case "exit" -> System.out.println("Good bye");
                default -> throw new RuntimeException("you entered an invalid value");
            }
        }
        scanner.close();
    }

    private BookDto createBookDto(Scanner scanner) {
        BookDto bookDto = new BookDto();
        System.out.print(messageForTitle);
        bookDto.setTitle(scanner.nextLine());
        System.out.print(messageForAuthor);
        bookDto.setAuthor(scanner.nextLine());
        System.out.print(messageForIsbn);
        bookDto.setIsbn(scanner.nextLine());
        System.out.print(messageForPages);
        bookDto.setPages(Integer.valueOf(scanner.nextLine()));
        System.out.print(messageForPrice);
        bookDto.setPrice(BigDecimal.valueOf(Double.parseDouble(scanner.nextLine())));
        System.out.println(messageForCover);
        bookDto.setCoverDto(BookCoverDto.valueOf(scanner.nextLine()));
        return bookDto;
    }


    public void userInteractForUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(MESSAGE1_FOR_USER);
        String result = scanner.nextLine();
        if (result.toLowerCase().equals("all")) {
            userService.getAll().forEach(b -> System.out.printf
                    ("User: id = %d, firstName = %s, lastName = %s\n", b.getId(), b.getFirstName(), b.getLastName()));
        } else if (result.toLowerCase().equals("exit")) {
            System.out.println("Good bye");
            return;
        } else {
            throw new RuntimeException("you entered an invalid value");
        }
        System.out.print(MESSAGE2_FOR_USER);
        String resultHelp = scanner.nextLine();
        try {
            Long id = Long.parseLong(resultHelp);
            System.out.print(MESSAGE3_FOR_USER);
            String resultAction = scanner.nextLine();
            if (resultAction.toLowerCase().equals("v")) {
                System.out.println(userService.getById(id));
            } else if (resultAction.toLowerCase().equals("d")) {
                userService.delete(id);
            } else if (resultAction.toLowerCase().equals("u")) {
                UserDto userDto = createUserDto(scanner);
                userDto.setId(id);
                userService.update(userDto);
            } else {
                throw new RuntimeException("you entered an invalid value");
            }
        } catch (NumberFormatException e) {
            switch (resultHelp.toLowerCase()) {
                case "create" -> userService.create(createUserDto(scanner));
                case "exit" -> System.out.println("Good bye");
                default -> throw new RuntimeException("you entered an invalid value");
            }
        }
        scanner.close();
    }

    private UserDto createUserDto(Scanner scanner) {
        UserDto userDto = new UserDto();
        System.out.print(messageForFirstName);
        userDto.setFirstName(scanner.nextLine());
        System.out.print(messageForLastName);
        userDto.setLastName(scanner.nextLine());
        System.out.print(messageForEmail);
        userDto.setEmail(scanner.nextLine());
        System.out.print(messageForPassword);
        userDto.setPassword(scanner.nextLine());
        System.out.print(messageForRole);
        userDto.setUserRoleDto(UserRoleDto.valueOf(scanner.nextLine()));
        return userDto;
    }
}
