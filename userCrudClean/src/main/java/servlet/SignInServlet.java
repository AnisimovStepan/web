package servlet;

import dao.DaoException;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/sign-in")
public class SignInServlet extends HttpServlet {
    private static final long serialVersionUID = 4252890072945341107L;
    
    private static final Logger log = LogManager.getLogger(SignInServlet.class.getName());
    
    private UserService userService = UserServiceImpl.getInstance();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        
        if ((user.getLogin() == null) || user.getLogin().equals("") ||
                (user.getPassword() == null)) {
            req.setAttribute("errorMessage", "Login or password is incorrect.");
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
        
        // Find user by login and compare password
        try {
            User userFromDb = userService.getByLogin(user.getLogin());
            // If user not found or passwords NOT equals
            if (userFromDb == null || !userFromDb.getPassword().equals(user.getPassword())) {
                req.setAttribute("errorMessage", "Login or password is incorrect.");
                getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                return;
            }
            
            log.info("Sign in success. Login: {}, role: {}.", userFromDb.getLogin(), userFromDb.getRole());
    
            // Get the old session and invalidate
            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            // Generate a new session
            HttpSession newSession = req.getSession(true);
    
            // Setting session to expiry in 5 mins
            newSession.setMaxInactiveInterval(5*60);
    
            newSession.setAttribute("welcomeUser", userFromDb);
            // resp.addCookie(new Cookie("login", userFromDb.getLogin()));
            // resp.addCookie(new Cookie("role", userFromDb.getRole().name()));
    
            switch (userFromDb.getRole()) {
                case USER:
                    resp.sendRedirect(req.getContextPath() + "/user"); break;
                case ADMIN:
                    resp.sendRedirect(req.getContextPath() + "/admin"); break;
            }
        } catch (DaoException e) {
            log.info("Can't find user or db exception ({}, {})", user.getLogin(), e.getMessage());
            
            req.setAttribute("errorMessage", "Service internal error.");
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
