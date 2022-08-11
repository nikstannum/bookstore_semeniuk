package main.java.com.belhard.dao;

import main.java.com.belhard.dao.entity.User;
import java.util.List;

public interface UserDao {
    User create(User user);

    User get(long id);

    List<User> getAll();

    User getUserByEmail(String email);

    List<User> getUsersByLastName(String lastName);

    int countAllUsers();

    User update(User user);

    boolean delete(long id);
}
