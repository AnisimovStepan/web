package com.userCrudSpring.util;

import com.userCrudSpring.model.Role;
import com.userCrudSpring.model.RoleType;
import com.userCrudSpring.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserUtil {
    // public static User getUserInstanceByParams(HttpServletRequest req) {
    //     User user = new User();
    //     user.setLogin(req.getParameter("login"));
    //     user.setPassword(req.getParameter("password"));
    //     // user.setRole(RoleType.valueOf(req.getParameter("role")));
    //     user.setFirstName(req.getParameter("first-name"));
    //     user.setLastName(req.getParameter("last-name"));
    //     user.setMiddleName(req.getParameter("middle-name"));
    //     return user;
    // }
    
    // Check user fields and send error to request attr
    public static boolean invalidUserFields(User user) {
        // Send error message
        String emptyStr = "";
        return (user.getLogin() == null) || (user.getFirstName() == null) || (user.getLastName() == null) ||
                (user.getLogin().equals(emptyStr)) || (user.getFirstName().equals(emptyStr)) ||
                (user.getPassword().equals(emptyStr)) || (user.getLastName().equals(emptyStr));
    }

    
    public static String determineTargetUrl(Authentication authentication) {
        return determineTargetUrl(authentication.getAuthorities());
    }
    
    public static String determineTargetUrl(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(RoleType.ADMIN.name())) {
                return "/admin";
            } else if (grantedAuthority.getAuthority().equals(RoleType.USER.name())) {
                return "/user";
            }
        }
        // System.out.println("Has't url for role");
        return null;
    }
}
