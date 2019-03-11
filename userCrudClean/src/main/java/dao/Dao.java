package dao;

import java.util.List;

public interface Dao<T> {
    // SELECT * from ...
    List<T> getAll() throws DaoException;

    // INSERT INTO ...
    void add(T t) throws DaoException;

    // DELETE ...
    void remove(T t) throws DaoException;

    // Update ...
    void save(T t) throws DaoException;
    
    // SELECT  ... WHERE
    T get(Object findValue) throws DaoException;
}
