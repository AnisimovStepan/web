package dao;

abstract class AbstractDao<T> implements Dao<T> {
    // Сonstant to save memory
    static final QueryPair[] EMPTY_QUERY_PAIR_ARRAY = new QueryPair[0];
    static final String[] EMTY_STRING_ARRAY = new String[0];
}
