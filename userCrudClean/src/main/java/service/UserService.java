package service;

import dao.DaoException;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void add(User user) throws DaoException;
    
    void remove(String login) throws DaoException;
    
    void save(User user) throws DaoException;
    
    User getByLogin(String login) throws DaoException;
    
    List<User> getUsers() throws DaoException;
}
