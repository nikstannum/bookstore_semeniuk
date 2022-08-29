package com.belhard.controller;

import com.belhard.dao.connection.DataSource;
import com.belhard.dao.impl.UserDaoImpl;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;
import com.belhard.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UsersCommand extends HttpServlet {
    private final UserService service = new UserServiceImpl(new UserDaoImpl(DataSource.INSTANCE));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> users = service.getAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("jsp/users.jsp").forward(req, resp);
    }
}
