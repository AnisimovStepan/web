package com.userCrudSpring.dao;

import com.userCrudSpring.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class RoleDaoHibernateImpl implements Dao<Role> {
    @PersistenceContext
    private EntityManager em;
    
    
    @Override
    public List<Role> getAll() {
        return em.createQuery("FROM Role", Role.class).getResultList();
    }
    
    @Override
    public void add(Role role) {
        em.persist(role);
    }
    
    @Override
    public void remove(Role role) {
        em.remove(em.contains(role) ? role : em.merge(role));
    }
    
    @Override
    public void update(Role role) {
        em.merge(role);
    }
    
    @Override
    public Role get(Object findValue) {
        TypedQuery<Role> query = em.createQuery("FROM Role R WHERE R.role = :role", Role.class);
        query.setParameter("role", findValue);
        List<Role> list = query.getResultList();
    
        if (list.isEmpty()) return null;
        return list.get(0);
    }
}
