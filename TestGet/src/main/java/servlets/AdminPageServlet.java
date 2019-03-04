package servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.AccountServerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPageServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(AdminPageServlet.class.getName());
    public static final String ADMIN_URL = "/admin";
    private final AccountServerImpl accountServer;
    
    public AdminPageServlet(AccountServerImpl accountServer) {
        this.accountServer = accountServer;
    }
    
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html;charset=utf-8");
        
        int limit = accountServer.getUsersLimit();
        int count = accountServer.getUsersCount();
        
        logger.info("Limit: {}. Count {}", limit, count);
    
        response.getWriter().println(limit);
        
        // if (limit > count) {
        //     logger.info("User pass");
        //     accountServer.addNewUser();
        //     response.getWriter().println("Hello, world!");
        //     response.setStatus(HttpServletResponse.SC_OK);
        // } else {
        //     logger.info("User were rejected");
        //     response.getWriter().println("Server is closed for maintenance!");
        //     response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // }
    }
}
