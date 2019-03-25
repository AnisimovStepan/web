package com.crud.service;

import com.crud.dao.Dao;
import com.crud.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final Dao<Role> roleDao;
    
    @Autowired
    public RoleServiceImpl(Dao<Role> RoleDao) {
        this.roleDao = RoleDao;
    }
    
    @Override
    public void add(Role role) {
        roleDao.add(role);
    }
    
    @Override
    public void remove(String role) {
        roleDao.remove(getByRole(role));
        
    }
    
    @Override
    public void update(Role role) {
        roleDao.update(role);
    }
    
    @Override
    public Role getByRole(String role) {
        return roleDao.get(role);
    }
    
    @Override
    public List<Role> getRoles() {
        return roleDao.getAll();
    }
}
