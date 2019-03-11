package factory;

import dao.UserDao;
import dao.UserDaoJdbcImpl;

public class DaoJdbcFactory implements AbstractDaoFactory {
    @Override
    public UserDao createUserDao() {
        return UserDaoJdbcImpl.getInstance();
    }
}
