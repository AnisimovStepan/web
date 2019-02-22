package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Endpoint /signin
public class SignInServlet extends HttpServlet {
    private AccountService<String, UserProfile> accountService;
    
    public SignInServlet(AccountService<String, UserProfile> accountService) {
        this.accountService = accountService;
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
        
        // Try to get user profile
        UserProfile userProfile = accountService.getAccount(login);
        // If user not exist on account service map, go away
        if (userProfile == null) {
            resp.getWriter().println("Unauthorized");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        // If password is not equals
        if (!password.equals(userProfile.getPassword())) {
            resp.getWriter().println("Incorrect login or password.");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        // All is fina, return info about found user
        resp.getWriter().println(String.format("Authorized: %s", userProfile.getLogin()));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
