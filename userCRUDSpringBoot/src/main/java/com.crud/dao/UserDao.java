package com.crud.dao;

import com.crud.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends Dao<User> {
    // Get by id
    User findById(long id);
    // Get by login
    default User findByLogin(String login) {
        return get(login);
    }
}
