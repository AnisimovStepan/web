package com.userCrudSpring.dao;

import com.userCrudSpring.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends Dao<User> {
    // Get by login
    default User findByLogin(String login) {
        return get(login);
    }
}
