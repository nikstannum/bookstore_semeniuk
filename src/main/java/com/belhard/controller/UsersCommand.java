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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/users")
public class UsersCommand extends HttpServlet {
    private final DataSource dataSource = DataSource.INSTANCE;
    private final UserService service = new UserServiceImpl(new UserDaoImpl(dataSource));
    private static final Logger log = LogManager.getLogger(UsersCommand.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> users = service.getAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("jsp/users.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            dataSource.close();
            log.info("dataSource successfully destroyed");
        } catch (Exception e) {
            log.error(e + " dataSource didn't destroy");
        }
    }
}
