package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Endpoint /signup
public class SignUpServlet extends HttpServlet {
    private AccountService<String, UserProfile> accountService;
    
    public SignUpServlet(AccountService<String, UserProfile> accountService) {
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
        
        // Create userProfile with correct data
        UserProfile userProfile = new UserProfile(login, password);
        // If user already exist go away
        if (accountService.getAccount(userProfile.getLogin()) != null) {
            resp.getWriter().println(String.format("User with login \"%s\" already exist.", userProfile.getLogin()));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // If all is fine, add user in account service map
        accountService.setAccount(userProfile.getLogin(), userProfile);
    
        resp.getWriter().println(String.format("User %s successfully registered.", userProfile.getLogin()));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
