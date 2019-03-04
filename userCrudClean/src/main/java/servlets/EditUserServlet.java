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

@WebServlet("/edit-user")
public class EditUserServlet extends HttpServlet {
    private static final long serialVersionUID = -757881318885776602L;
    
    private static final Logger log = LogManager.getLogger(EditUserServlet.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set user by login
        try {
            User user = UserDao.getUserByLogin(req.getParameter("login"));
            req.setAttribute("user", user);
        } catch (SQLException e) {
            req.setAttribute("errorMessage", String.format("Can't get user by login (%s).", req.getParameter("login")));
        }
    
        // Set header param
        req.setAttribute("blockHeader", "Edit User");
        req.setAttribute("servletUrl", req.getRequestURI());
        // Forward to create user jsp
        getServletContext().getRequestDispatcher("/WEB-INF/view/userView.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserUtils.getUserInstanceByParams(req);
    
        // Check input user data if is false
        if (UserUtils.invalidUserFields(user, req)) {
            // Forward to create user jsp
            getServletContext().getRequestDispatcher("/WEB-INF/view/userView.jsp").forward(req, resp);
            return;
        }
    
        try {
            // Get from db user
            User dbUser = UserDao.getUserByLogin(user.getLogin());
            // Copy id
            user.setId(dbUser.getId());
            // Update user
            UserDao.save(user);
            // Redirect if all is fine to root
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (SQLException e) {
            log.info(e.getMessage());
            e.printStackTrace();
            // If some db error
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("user", user);
            
            req.setAttribute("blockHeader", "Edit User");
            req.setAttribute("servletUrl", req.getRequestURI());
    
            getServletContext().getRequestDispatcher("/WEB-INF/view/userView.jsp").forward(req, resp);
        }
    }
}
