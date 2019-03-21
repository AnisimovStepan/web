package com.userCrudSpring.service;

import com.userCrudSpring.dao.UserDao;
import com.userCrudSpring.model.Role;
import com.userCrudSpring.model.RoleType;
import com.userCrudSpring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserDao userDao;
    
    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // if (login == null) {throw new UsernameNotFoundException("User not found");}
        // Get user by login or any other string value
        User user = userDao.findByLogin(login);
        if (user == null) { throw new UsernameNotFoundException("User not found"); }
        
        // Get roles by this user and add it on hash set
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getRole().name()));
        }
        
        // On user data we have object which check login pass and roles an next get session or auth status
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), roles);
    }
}
