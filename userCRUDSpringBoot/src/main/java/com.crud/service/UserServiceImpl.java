package com.crud.service;

import com.crud.dao.UserDao;
import com.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
    }
    
    @Override
    public void remove(String login) {
        userDao.remove(userDao.findByLogin(login));
    }
    @Override
    public void remove(long id) {
        userDao.remove(userDao.findById(id));
    }
    
    @Override
    public void update(User user) {
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(user);
    }
    
    @Override
    public User getByLogin(String login) {
        return userDao.findByLogin(login);
    }
    
    @Override
    public User getById(long id) {
        return userDao.findById(id);
    }
    
    // @Transactional
    @Override
    public List<User> getUsers() {
        return userDao.getAll();
    }
}
