package com.crud.util;

import com.crud.model.RoleType;
import com.crud.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserUtil {
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
        return null;
    }
}
