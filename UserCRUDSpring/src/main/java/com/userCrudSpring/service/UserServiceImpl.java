package com.userCrudSpring.service;

import com.userCrudSpring.dao.UserDao;
import com.userCrudSpring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(user);
    }
    
    @Override
    public User getByLogin(String login) {
        return userDao.findByLogin(login);
    }
    
    // @Transactional
    @Override
    public List<User> getUsers() {
        return userDao.getAll();
    }
}
