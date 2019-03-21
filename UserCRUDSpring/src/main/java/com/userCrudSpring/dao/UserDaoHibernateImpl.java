package com.userCrudSpring.dao;

import com.userCrudSpring.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class UserDaoHibernateImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<User> getAll() {
        return em.createQuery("FROM User ORDER BY id", User.class).getResultList();
    }
    
    @Override
    public void add(User user) {
        em.merge(user);
    }
    
    @Override
    public void remove(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
        // em.remove(user);
    }
    
    @Override
    public void update(User user) {
        em.merge(user);
    }
    
    @Override
    public User get(Object findValue) {
        TypedQuery<User> query = em.createQuery("FROM User U WHERE U.login = :login", User.class);
        query.setParameter("login", findValue);
        List<User> list = query.getResultList();
        
        if (list.isEmpty()) return null;
        return list.get(0);
    }
}
