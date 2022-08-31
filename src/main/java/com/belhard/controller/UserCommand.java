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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/user")
public class UserCommand extends HttpServlet {
    private final DataSource dataSource = DataSource.INSTANCE;
    private final UserService service = new UserServiceImpl(new UserDaoImpl(dataSource));
    private static final Logger log = LogManager.getLogger(UserCommand.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        try {
            Long id = Long.parseLong(idStr);
            UserDto dto = service.get(id);
            req.setAttribute("user", dto);
            req.getRequestDispatcher("jsp/user.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.setStatus(404);
            resp.getWriter().write("invalid value");
        } catch (RuntimeException e) {
            resp.setStatus(400);
            resp.getWriter().write("not exist user with id=" + idStr);
        }
    }
    @Override
    public void destroy() {
        try {
            dataSource.close();
            log.info("dataSource successfully destroyed");
        } catch (Exception e) {
            log.error(e + " dataSource didn't destroy");
        }
    }
}
