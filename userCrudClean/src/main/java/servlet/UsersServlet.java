package servlet;

import dao.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class UsersServlet extends HttpServlet {
    private static final long serialVersionUID = 694117616733979380L;

    private static final Logger log = LogManager.getLogger(UsersServlet.class.getName());
    
    private UserService userService = UserServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Store list of users
            req.setAttribute("userList", userService.getUsers());
        } catch (DaoException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        // Forward to /tableView.jsp
        getServletContext().getRequestDispatcher("/WEB-INF/view/tableView.jsp").forward(req, resp);
    }
}