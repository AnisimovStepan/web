package dao;

import config.DBConfigHelper;
import dao.executor.Executor;
import model.User;
import util.UserUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {
    private static final String SCHEMA_NAME = "\"stepic_jdbc\".\"user\"";
    private static final String INSERT_USER_TEMPLATE =
            String.format("INSERT INTO %s (\"id\", \"login\", \"first_name\", \"last_name\", \"middle_name\") VALUES (DEFAULT, ?, ?, ?, ?)", SCHEMA_NAME);
    
    private static final String DELETE_USER_TEMPLATE =
            String.format("DELETE FROM %s WHERE \"id\" = ? AND \"login\" = ?", SCHEMA_NAME);
    
    private static final String UPDATE_USER_TEMPLATE =
            String.format("UPDATE %s SET \"login\" = ?, \"first_name\" = ?, \"last_name\" = ?, \"middle_name\" = ? WHERE \"id\" = ?", SCHEMA_NAME);
    
    private static final String SELECT_USER_BY_LOGIN_TEMPLATE =
            String.format("SELECT t.* FROM %s t WHERE t.login = ?", SCHEMA_NAME);
    
    private static final String SELECT_ALL_USERS_TEMPLATE =
            String.format("SELECT t.* FROM %s t ORDER BY t.id", SCHEMA_NAME);
    
    private Connection connection;
    private static UserDao userDao;
    
    public static UserDao getInstance() {
        if (userDao == null) {
            userDao = new UserDaoJdbcImpl();
        }
        
        return userDao;
    }
    
    private UserDaoJdbcImpl() {
        this.connection = DBConfigHelper.getConnection();
    }
    
    @Override
    public List<User> getAll() throws DaoException {
        try {
            return Executor.execQuery(connection,
                    new AbstractMap.SimpleEntry<>(
                            SELECT_ALL_USERS_TEMPLATE,
                            null
                    ),
                    rs -> {
                        try {
                            List<User> list = new ArrayList<>();
                            while (rs.next()) {
                                list.add(UserUtil.getUserInstanceByResultSet(rs));
                            }
                            return list;
                        } catch (SQLException e) {
                            System.out.println(String.format("Failed to work with result set (%s).", e.getMessage()));
                            e.printStackTrace();
                            return Collections.emptyList();
                        }
                    });
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
    
    @Override
    public void add(User user) throws DaoException {
        try {
            Executor.execQuery(connection,
                    new AbstractMap.SimpleEntry<>(
                            INSERT_USER_TEMPLATE,
                            new Object[]{
                                    user.getLogin(),
                                    user.getFirstName(),
                                    user.getLastName(),
                                    user.getMiddleName()
                            }
                    ));
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
    
    @Override
    public void remove(User user) throws DaoException {
        try {
            Executor.execQuery(connection,
                    new AbstractMap.SimpleEntry<>(
                            DELETE_USER_TEMPLATE,
                            new Object[]{
                                    user.getId(),
                                    user.getLogin(),
                            }
                    ));
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        
    }
    
    @Override
    public void save(User user) throws DaoException {
        try {
            Executor.execQuery(connection,
                    new AbstractMap.SimpleEntry<>(
                            UPDATE_USER_TEMPLATE,
                            new Object[]{
                                    user.getLogin(),
                                    user.getFirstName(),
                                    user.getLastName(),
                                    user.getMiddleName(),
                                    user.getId(),
                            }
                    ));
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
    
    @Override
    public User get(Object findValue) throws DaoException {
        try {
            return Executor.execQuery(connection,
                    new AbstractMap.SimpleEntry<>(
                            SELECT_USER_BY_LOGIN_TEMPLATE,
                            new Object[]{
                                    findValue
                            }
                    ),
                    resultSet -> {
                        try {
                            resultSet.next();
                            return UserUtil.getUserInstanceByResultSet(resultSet);
                        } catch (SQLException e) {
                            System.out.println(String.format("Failed to work with result set (%s).", e.getMessage()));
                            e.printStackTrace();
                            return null;
                        }
                    });
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
