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
import java.util.List;

@WebServlet("/create-user")
public class CreateUserServlet extends HttpServlet {
    private static final long serialVersionUID = -974743633305019061L;
    
    private static final Logger log = LogManager.getLogger(CreateUserServlet.class.getName());
    
    // Show user creation page.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set header param
        req.setAttribute("blockHeader", "Create User");
        req.setAttribute("servletUrl", req.getRequestURI());
        // Forward to create user jsp
        getServletContext().getRequestDispatcher("/WEB-INF/view/userView.jsp").forward(req, resp);
    }
    
    // When the user enters the user information, and click Submit.
    // This method will be called.
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
            UserDao.add(user);
            // Redirect if all is fine to root
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (SQLException e) {
            log.info(e.getMessage());
            e.printStackTrace();
            // If some db error
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("user", user);
            
            // Set header param
            req.setAttribute("blockHeader", "Create User");
            req.setAttribute("servletUrl", req.getRequestURI());
            
            getServletContext().getRequestDispatcher("/WEB-INF/view/userView.jsp").forward(req, resp);
        }
    }
}
