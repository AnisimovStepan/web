package service;

import dao.DaoException;
import dao.UserDao;
import factory.FactoryGetter;
import model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private static UserService userService;
    
    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        
        return userService;
    }
    
    private UserServiceImpl() {
        
        this.userDao = FactoryGetter.getAbstractDaoFactory().createUserDao();
        // this.userDao = UserDaoHibernateImpl.getInstance();
    }
    
    @Override
    public void add(User user) throws DaoException {
        userDao.add(user);
    }
    
    @Override
    public void remove(String login) throws DaoException {
        userDao.remove(userDao.findByLogin(login));
    }
    
    @Override
    public void save(User user) throws DaoException {
        userDao.save(user);
    }
    
    @Override
    public User getByLogin(String login) throws DaoException {
        return userDao.findByLogin(login);
    }
    
    @Override
    public List<User> getUsers() throws DaoException {
        return userDao.getAll();
    }
}
