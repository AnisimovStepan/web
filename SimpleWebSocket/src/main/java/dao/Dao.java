package dao;

import java.util.List;

interface Dao<T> {
    // SELECT * from ...
    List<T> getAll();
    
    // INSERT INTO ...
    long add(T t);
    
    // DELETE ...
    boolean remove(T t);
    
    // SELECT * from ... WHERE
    // T get(T t);
}

