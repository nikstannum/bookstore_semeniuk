package com.belhard;

import com.belhard.dao.BookDao;
import com.belhard.dao.BookDaoImpl;
import com.belhard.util.DataSource;
import com.belhard.util.UserInteraction;

public class Main {

    public static void main(String[] args) {

        try (DataSource dataSource = new DataSource()) {
            BookDao bookDao = new BookDaoImpl(dataSource);
            UserInteraction.userInteract(bookDao);
        }
    }
}
