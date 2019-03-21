package com.userCrudSpring.service;

import com.userCrudSpring.model.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {
    @PreAuthorize("hasAuthority('ADMIN')")
    void add(User user);
    
    @PreAuthorize("hasAuthority('ADMIN')")
    void remove(String login);
    
    @PreAuthorize("hasAuthority('ADMIN')")
    void update(User user);
    
    User getByLogin(String login);
    
    List<User> getUsers();
}
