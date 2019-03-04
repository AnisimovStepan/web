package dao;

import model.Message;
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

public class MessageDao implements Dao<Message> {
    private SessionFactory sessionFactory;
    
    public MessageDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public List<Message> getAll() {
        try (Session session = sessionFactory.openSession()) {
            // Get criteria fabric
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Get query by class (need to get this type)
            CriteriaQuery<Message> query = builder.createQuery(Message.class);
            // Set root of graph
            Root<Message> root = query.from(Message.class);
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
    
    @Override
    public long add(Message message) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long id = (Long) session.save(message);
            transaction.commit();
            return id;
        }
        catch (HibernateException e) {
            System.out.println(String.format("Failed to add message \"%s\" (%s).", message.getMessage(), e.getMessage()));
            // if transaction steel alive - rollback changes
            if (transaction != null) {
                transaction.rollback();
            }
            return -1;
        }
    }
    
    @Override
    public boolean remove(Message message) {
        return false;
    }
}
