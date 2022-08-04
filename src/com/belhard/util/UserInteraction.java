package com.belhard.util;

import com.belhard.beans.Book;
import com.belhard.dao.BookDao;
import java.util.Scanner;

public class UserInteraction {

    public static void userInteract(BookDao bookDao) {
        final String MESSAGE1 = "If you want:\n\t1) to view all books, please, insert 'all' and press ENTER\n" +
                "\t2) to exit from app, please, insert 'exit' and press ENTER: ";
        final String MESSAGE2 = "If you want:\n\t1) to view detailed information about the book or to delete any book from list" +
                " or update any book, please, insert book's id and press ENTER\n\t" +
                "2) to create and insert new book into list, please, insert 'create' and press ENTER\n\t" +
                "3) to exit from app, please, insert 'exit' and press ENTER: ";
        final String MESSAGE3 = "If you want to view detail information about the book insert 'v' and press ENTER" +
                ",\n if you want to update the book insert 'u' and press ENTER" +
                ",\n if you want to delete the book insert 'd' and press ENTER: ";

        Scanner scanner = new Scanner(System.in);
        System.out.print(MESSAGE1);
        String result1 = scanner.nextLine();
        if (result1.toLowerCase().equals("all")) {
            bookDao.getAll().forEach(b -> System.out.printf
                    ("Book: id = %d, title = %s, author = %s\n", b.getId(), b.getTitle(), b.getAuthor()));
        } else if (result1.toLowerCase().equals("exit")) {
            System.out.println("Good bye");
            System.exit(0);
        } else {
            System.exit(1);
        }
        System.out.print(MESSAGE2);
        String result2 = scanner.nextLine();
        try {
            Long id = Long.parseLong(result2);
            System.out.print(MESSAGE3);
            String result21 = scanner.nextLine();
            if (result21.toLowerCase().equals("v")) {
                System.out.println(bookDao.get(id));
            } else if (result21.toLowerCase().equals("d")) {
                bookDao.delete(id);
            } else if (result21.toLowerCase().equals("u")) {
                Book book = createBook(scanner);
                book.setId(id);
                bookDao.update(book);
            } else {
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            switch (result2.toLowerCase()) {
                case "create" -> bookDao.create(createBook(scanner));
                case "exit" -> System.exit(0);
                default -> System.exit(1);
            }
        }
    }

    private static Book createBook(Scanner scanner) {
        final String messageForTitle = "insert title of book: ";
        final String messageForAuthor = "insert author of book: ";
        final String messageForIsbn = "insert isbn of book: ";
        final String messageForPages = "insert number of pages: ";
        final String messageForPrice = "insert price : ";

        Book book = new Book();
        System.out.print(messageForTitle);
        book.setTitle(scanner.nextLine());
        System.out.print(messageForAuthor);
        book.setAuthor(scanner.nextLine());
        System.out.print(messageForIsbn);
        book.setIsbn(scanner.nextLine());
        System.out.print(messageForPages);
        book.setPages(scanner.nextInt());
        System.out.print(messageForPrice);
        book.setPrice(scanner.nextBigDecimal());

        return book;
    }
}
