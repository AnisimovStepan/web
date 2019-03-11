package dao;

import config.DBConfigHelper;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;
    private static UserDao userDao;
    
    public static UserDao getInstance() {
        if (userDao == null) {
            userDao = new UserDaoHibernateImpl();
        }
        
        return userDao;
    }
    
    private UserDaoHibernateImpl() {
        this.sessionFactory = DBConfigHelper.getSessionFactory();
    }
    
    @Override
    public List<User> getAll() throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }
    
    @Override
    public void add(User user) throws DaoException {
        Transaction transaction = null;
        
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            // if transaction steel alive - rollback changes
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }
    
    @Override
    public void remove(User user) throws DaoException {
        Transaction transaction = null;
        
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            // if transaction steel alive - rollback changes
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }
    
    @Override
    public void save(User user) throws DaoException {
        Transaction transaction = null;
        
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            // if transaction steel alive - rollback changes
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage());
        }
    }
    
    @Override
    public User get(Object findValue) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User U WHERE U.login = :login", User.class);
            query.setParameter("login", findValue);
            List<User> list = query.list();
            
            if (list.isEmpty()) return null;
            return list.get(0);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }
}
