package com.crud.service;

import com.crud.model.Role;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface RoleService {
    @PreAuthorize("hasAuthority('ADMIN')")
    void add(Role role);
    
    @PreAuthorize("hasAuthority('ADMIN')")
    void remove(String role);
    
    @PreAuthorize("hasAuthority('ADMIN')")
    void update(Role role);
    
    Role getByRole(String role);
    
    List<Role> getRoles();
}
