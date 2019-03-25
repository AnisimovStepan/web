package com.crud.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.crud.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // authentication.getPrincipal()
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }
    
    private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = UserUtil.determineTargetUrl(authentication);
        
        if (response.isCommitted()) {
            System.out.println("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        
        // Work with session set ttl and attr current user
        final HttpSession session = request.getSession(true);
        // Setting session to expiry in 100 mins
        session.setMaxInactiveInterval(100*60);

        session.setAttribute("welcomeUser", authentication.getPrincipal());
        if (targetUrl == null) {
            session.setAttribute("errorMessage", "User has no role.");
            targetUrl = "/";
        }
        
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
    
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
