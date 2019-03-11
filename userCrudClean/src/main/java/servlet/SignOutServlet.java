package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/sign-out")
public class SignOutServlet extends HttpServlet {
    private static final long serialVersionUID = 4086507966008860352L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //invalidate the session if exists
        HttpSession session = req.getSession(false);
        if (session != null){
            session.invalidate();
        }
        //Log out Back
        // resp.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        // resp.addHeader("Pragma", "no-cache"); // HTTP 1.0.
        // resp.addHeader("Expires","0"); // Proxies.
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
