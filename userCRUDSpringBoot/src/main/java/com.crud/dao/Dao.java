package com.crud.dao;

import java.util.List;

public interface Dao<T> {
    // SELECT * from ...
    List<T> getAll();

    // INSERT INTO ...
    void add(T t);

    // DELETE ...
    void remove(T t);

    // Update ...
    void update(T t);
    
    // SELECT  ... WHERE
    T get(Object findValue);
}
