package servlets;

import dao.UserDao;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("")
public class UsersServlet extends HttpServlet {
    private static final long serialVersionUID = 694117616733979380L;

    private static final Logger log = LogManager.getLogger(UsersServlet.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> list = UserDao.getAll();
            // Store list of users
            req.setAttribute("userList", list);
        } catch (SQLException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        // Forward to /userView.jsp
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
