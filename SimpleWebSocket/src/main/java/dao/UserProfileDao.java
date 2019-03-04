package dao;

import model.UserProfile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

public class UserProfileDao implements Dao<UserProfile> {
    private SessionFactory sessionFactory;
    
    public UserProfileDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public long add(UserProfile userProfile) {
        Transaction transaction = null;
        
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long id = (Long) session.save(userProfile);
            transaction.commit();
            return id;
        }
        catch (HibernateException e) {
            System.out.println(String.format("Failed to add user \"%s\" (%s).", userProfile, e.getMessage()));
            // if transaction steel alive - rollback changes
            if (transaction != null) {
                transaction.rollback();
            }
            return -1;
        }
    }
    
    @Override
    public boolean remove(UserProfile userProfile) {
        Transaction transaction = null;
    
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(userProfile);
            transaction.commit();
            return true;
        }
        catch (HibernateException e) {
            System.out.println(String.format("Failed to delete user \"%s\" (%s).", userProfile, e.getMessage()));
            // if transaction steel alive - rollback changes
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }
    
    public UserProfile getByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            // Get criteria fabric
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Get query by class (need to get this type)
            CriteriaQuery<UserProfile> query = builder.createQuery(UserProfile.class);
            // Set root of graph
            Root<UserProfile> root = query.from(UserProfile.class);
            // Condition to get user by login
            query.select(root).where(builder.equal(root.get("login"), login));
            // Execute and get result
            return session.createQuery(query).getSingleResult();
        }
        catch (Exception e) {
            System.out.println(String.format("Not found user by login \"%s\" (%s).", login, e.getMessage()));
            return null;
        }
    }
    
    @Override
    public List<UserProfile> getAll() {
        try (Session session = sessionFactory.openSession()) {
            // Get criteria fabric
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Get query by class (need to get this type)
            CriteriaQuery<UserProfile> query = builder.createQuery(UserProfile.class);
            // Set root of graph
            Root<UserProfile> root = query.from(UserProfile.class);
            // Condition to get user by login
            query.select(root);
            // Execute and get result
            return session.createQuery(query).getResultList();
        }
        catch (Exception e) {
            System.out.println(String.format("Not found any user (%s).", e.getMessage()));
            return Collections.emptyList();
        }
    }
}
