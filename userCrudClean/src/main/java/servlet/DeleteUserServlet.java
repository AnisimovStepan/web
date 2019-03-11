package servlet;

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

@WebServlet("/admin/delete-user")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = -9211055415834937143L;
    
    private static final Logger log = LogManager.getLogger(DeleteUserServlet.class.getName());
    
    private UserService userService = UserServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Remove user by login
            userService.remove(req.getParameter("login"));
            
            resp.sendRedirect(req.getContextPath() + "/admin");
        } catch (Exception e) {
            log.info("Can't delete user by login ({}).", req.getParameter("login"));
            // req.setAttribute("errorMessage", String.format("Can't delete user by login (%s).", req.getParameter("login")));
            // // If something wrong on delete
            // // Set header param
            // req.setAttribute("blockHeader", "Edit User");
            // req.setAttribute("servletUrl", "/edit-user");
            // // Forward to create user jsp
            // getServletContext().getRequestDispatcher("/WEB-INF/view/userView.jsp").forward(req, resp);
            resp.sendRedirect(req.getContextPath() + "/admin");
        }
    }
}
