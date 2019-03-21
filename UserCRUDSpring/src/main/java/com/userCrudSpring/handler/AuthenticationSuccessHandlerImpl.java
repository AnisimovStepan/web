package com.userCrudSpring.handler;

import com.userCrudSpring.model.RoleType;
import com.userCrudSpring.model.User;
import com.userCrudSpring.util.UserUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

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
        
        redirectStrategy.sendRedirect(request, response, targetUrl);

        // Work with session set ttl and attr current user
        final HttpSession session = request.getSession(false);
        if (session != null) {
            // Setting session to expiry in 5 mins
            session.setMaxInactiveInterval(100*60);
    
            session.setAttribute("welcomeUser", authentication.getPrincipal());
        }
    }
    
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
    
    // public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
    //     this.redirectStrategy = redirectStrategy;
    // }
    //
    // protected RedirectStrategy getRedirectStrategy() {
    //     return redirectStrategy;
    // }
}
