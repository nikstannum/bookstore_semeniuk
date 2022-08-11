package main.java.com.belhard.controller;

import main.java.com.belhard.service.BookService;
import main.java.com.belhard.service.dto.BookDto;
import java.util.Scanner;

public class UserInteraction {
    private static final String MESSAGE1 = """
            If you want:
            \t1) to view all books, please, insert 'all' and press ENTER
            \t2) to exit from app, please, insert 'exit' and press ENTER: 
            """;
    private static final String MESSAGE2 = """
            If you want:
            \t1) to view detailed information about the book or to delete any book from list or update any book, please, insert book's id and press ENTER
            \t2) to create and insert new book into list, please, insert 'create' and press ENTER
            \t3) to exit from app, please, insert 'exit' and press ENTER: 
            """;
    private static final String MESSAGE3 = """
            If you want to view detail information about the book insert 'v' and press ENTER
            if you want to update the book insert 'u' and press ENTER
            if you want to delete the book insert 'd' and press ENTER: 
            """;

    private static final String messageForTitle = "insert title of book: ";
    private static final String messageForAuthor = "insert author of book: ";
    private static final String messageForIsbn = "insert isbn of book: ";
    private static final String messageForPages = "insert number of pages: ";
    private static final String messageForPrice = "insert price : ";

    private BookService bookService;

    public UserInteraction(BookService bookService) {
        this.bookService = bookService;
    }

    public void userInteract() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(MESSAGE1);
        String result = scanner.nextLine();
        if (result.toLowerCase().equals("all")) {
            bookService.getAll().forEach(b -> System.out.printf
                    ("Book: id = %d, title = %s, author = %s\n", b.getId(), b.getTitle(), b.getAuthor()));
        } else if (result.toLowerCase().equals("exit")) {
            System.out.println("Good bye");
            return;
        } else {
            throw new RuntimeException("you entered an invalid value");
        }
        System.out.print(MESSAGE2);
        String resultHelp = scanner.nextLine();
        try {
            Long id = Long.parseLong(resultHelp);
            System.out.print(MESSAGE3);
            String resultAction = scanner.nextLine();
            if (resultAction.toLowerCase().equals("v")) {
                System.out.println(bookService.get(id));
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
            switch (resultHelp.toLowerCase()) {
                case "create" -> bookService.create(createBookDto(scanner));
                case "exit" -> System.out.println("Good bye");
                default -> throw new RuntimeException("you entered an invalid value");
            }
        }
    }

    private static BookDto createBookDto(Scanner scanner) {
        BookDto bookDto = new BookDto();
        System.out.print(messageForTitle);
        bookDto.setTitle(scanner.nextLine());
        System.out.print(messageForAuthor);
        bookDto.setAuthor(scanner.nextLine());
        System.out.print(messageForIsbn);
        bookDto.setIsbn(scanner.nextLine());
        System.out.print(messageForPages);
        bookDto.setPages(scanner.nextInt());
        System.out.print(messageForPrice);
        bookDto.setPrice(scanner.nextBigDecimal());
        return bookDto;
    }
}
