package com.crud.service;

import com.crud.model.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {
    @PreAuthorize("hasAuthority('ADMIN')")
    void add(User user);
    
    @PreAuthorize("hasAuthority('ADMIN')")
    void remove(String login);
    @PreAuthorize("hasAuthority('ADMIN')")
    void remove(long id);
    
    @PreAuthorize("hasAuthority('ADMIN')")
    void update(User user);
    
    User getByLogin(String login);
    
    User getById(long id);
    
    List<User> getUsers();
}
