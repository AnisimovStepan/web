package com.userCrudSpring.handler;

import com.userCrudSpring.util.UserUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException, ServletException {
        String redirectUrl = "/";
        // If we have some data in session
        final HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("welcomeUser");
            redirectUrl = UserUtil.determineTargetUrl(user.getAuthorities());
        }
        
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}