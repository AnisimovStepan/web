package servlets;

import dao.UserProfileDao;
import model.UserProfile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Endpoint /signup
public class SignUpServlet extends HttpServlet {
    private UserProfileDao userProfileDao;
    
    public SignUpServlet(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        
        // Exit if word is incorrect
        if ((login == null || login.isEmpty()) || (password == null || password.isEmpty())) {
            resp.getWriter().println("Param \"login\" or \"password\" null or empty.");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        // Create userProfile with correct data
        UserProfile userProfile = new UserProfile(0, login, password);
        // If user already exist go away
        if (userProfileDao.get(userProfile) != null) {
            resp.getWriter().println(String.format("User with login \"%s\" already exist.", userProfile.getLogin()));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // If all is fine, add user in database
        if (userProfileDao.add(userProfile)) {
            resp.getWriter().println(String.format("User %s successfully registered.", userProfile.getLogin()));
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.getWriter().println("Internal server error.");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    
    }
}
