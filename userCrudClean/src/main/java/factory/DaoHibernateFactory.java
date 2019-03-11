package factory;

import dao.UserDao;
import dao.UserDaoHibernateImpl;

public class DaoHibernateFactory implements AbstractDaoFactory {
    @Override
    public UserDao createUserDao() {
        return UserDaoHibernateImpl.getInstance();
    }
}
