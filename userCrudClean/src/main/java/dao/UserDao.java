package dao;

import model.User;

public interface UserDao extends Dao<User> {
    // Get by login
    default User findByLogin(String login) throws DaoException {
        return get(login);
    }
}
