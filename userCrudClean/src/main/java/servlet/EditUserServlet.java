package servlet;

import dao.DaoException;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service.UserServiceImpl;
import util.UserUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/edit-user")
public class EditUserServlet extends HttpServlet {
    private static final long serialVersionUID = -757881318885776602L;
    
    private static final Logger log = LogManager.getLogger(EditUserServlet.class.getName());
    
    private UserService userService = UserServiceImpl.getInstance();
    private User userToEdit;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set user by login
        try {
            // Save user to change
            userToEdit = userService.getByLogin(req.getParameter("login"));
            req.setAttribute("user", userToEdit);
        } catch (DaoException e) {
            req.setAttribute("errorMessage", String.format("Can't getByLogin user by login (%s).", req.getParameter("login")));
        }
    
        // Set header param
        req.setAttribute("blockHeader", "Edit User");
        req.setAttribute("servletUrl", req.getRequestURI());
        // Forward to create user jsp
        getServletContext().getRequestDispatcher("/WEB-INF/view/userView.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserUtil.getUserInstanceByParams(req);
    
        // Check input user data if is false
        if (UserUtil.invalidUserFields(user, req)) {
            // Forward to create user jsp
            req.setAttribute("blockHeader", "Edit User");
            req.setAttribute("servletUrl", req.getRequestURI());
    
            req.setAttribute("user", user);
            
            getServletContext().getRequestDispatcher("/WEB-INF/view/userView.jsp").forward(req, resp);
            return;
        }
    
        try {
            // Copy id for save in db changed data
            user.setId(userToEdit.getId());
            // Update user
            userService.save(user);
            // Redirect if all is fine to root
            resp.sendRedirect(req.getContextPath() + "/admin");
        } catch (DaoException e) {
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
