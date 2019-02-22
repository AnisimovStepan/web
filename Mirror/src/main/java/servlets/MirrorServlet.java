package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Endpoint /mirror
public class MirrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mirrorWord = req.getParameter("key");
        // Exit if word is incorrect
        if (mirrorWord == null || mirrorWord.isEmpty()) {
            resp.getWriter().println("Param \"key\" is incorrect.");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        resp.getWriter().println(mirrorWord);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
