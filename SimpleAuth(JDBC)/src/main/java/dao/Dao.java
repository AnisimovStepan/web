package dao;

import java.util.List;

interface Dao<T> {
    // SELECT * from ...
    List<T> getAll();
    
    // SELECT * ... WHERE cond0 AND ... AND condN
    List<T> getAllWhere(QueryPair... conditions);
    
    // INSERT INTO ...
    boolean add(T t);
    
    // DELETE ...
    boolean remove(T t);
    
    // SELECT * from ... WHERE
    T get(T t);
    
    // SELECT * from ... WHERE id=
    // null if nothing found
    default T findById(long id) {
        List<T> structure = getAllWhere(new QueryPair("id=?", String.valueOf(id)));
        if (structure.isEmpty()) return null;
        return structure.get(0);
    }
}

