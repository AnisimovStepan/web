package servlets;

import dao.UserDao;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = -9211055415834937143L;
    
    private static final Logger log = LogManager.getLogger(DeleteUserServlet.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set user by login
        try {
            User user = UserDao.getUserByLogin(req.getParameter("login"));
            UserDao.remove(user);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (SQLException e) {
            req.setAttribute("errorMessage", String.format("Can't delete user by login (%s).", req.getParameter("login")));
            // If something wrong on delete
            // Set header param
            req.setAttribute("blockHeader", "Edit User");
            req.setAttribute("servletUrl", "/edit-user");
            // Forward to create user jsp
            getServletContext().getRequestDispatcher("/WEB-INF/view/userView.jsp").forward(req, resp);
        }
    }
}
