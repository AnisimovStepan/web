package factory;

import dao.UserDao;

public interface AbstractDaoFactory {
    UserDao createUserDao();
}
