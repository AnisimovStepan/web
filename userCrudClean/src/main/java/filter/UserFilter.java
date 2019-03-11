package filter;

import model.RoleType;
import model.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/user/*")
public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
    
        HttpSession session = req.getSession(false);
        // Checking whether the session exists
        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/");
            return;
        }
    
        // Check user role
        User wUser = (User) session.getAttribute("welcomeUser");
        if (wUser == null) {
            res.sendRedirect(req.getContextPath() + "/");
            return;
        }
        // if (wUser.getRole() != RoleType.ADMIN) {
        //     res.sendRedirect(req.getContextPath() + "/user");
        //     return;
        // }
    
        // Pass the request along the filter chain
        filterChain.doFilter(servletRequest, servletResponse);
        
    }
}
