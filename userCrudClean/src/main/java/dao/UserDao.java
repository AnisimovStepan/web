package dao;

import dao.executor.Executor;
import model.User;
import services.DBService;
import utils.UserUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class UserDao implements Dao<User> {
    private static final Connection connection = DBService.getConnection();
    
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
    
    // @Override
    public static List<User> getAll() throws SQLException {
        return Executor.execQuery(connection,
                new AbstractMap.SimpleEntry<>(
                        SELECT_ALL_USERS_TEMPLATE,
                        null
                ),
                rs -> {
                    try {
                        List<User> list = new ArrayList<>();
                        while (rs.next()) {
                            list.add(UserUtils.getUserInstanceByResultSet(rs));
                        }
                        return list;
                    }
                    catch (SQLException e) {
                        System.out.println(String.format("Failed to work with result set (%s).", e.getMessage()));
                        e.printStackTrace();
                        return Collections.emptyList();
                    }
                });
    }
    
    // @Override
    public static void add(User user) throws SQLException {
        Executor.execQuery(connection,
                new AbstractMap.SimpleEntry<>(
                        INSERT_USER_TEMPLATE,
                        new Object[] {
                                user.getLogin(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getMiddleName()
                        }
                ));
    }
    
    // @Override
    public static void remove(User user) throws SQLException {
        Executor.execQuery(connection,
                new AbstractMap.SimpleEntry<>(
                        DELETE_USER_TEMPLATE,
                        new Object[] {
                                user.getId(),
                                user.getLogin(),
                        }
                ));
    }
    
    // @Override
    public static void save(User user) throws SQLException {
        Executor.execQuery(connection,
                new AbstractMap.SimpleEntry<>(
                        UPDATE_USER_TEMPLATE,
                        new Object[] {
                                user.getLogin(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getMiddleName(),
                                user.getId(),
                        }
                ));
    }
    
    public static User getUserByLogin(String login) throws SQLException {
        return Executor.execQuery(connection,
                new AbstractMap.SimpleEntry<>(
                        SELECT_USER_BY_LOGIN_TEMPLATE,
                        new Object[] {
                                login
                        }
                ),
                resultSet -> {
                    try {
                        resultSet.next();
                        return UserUtils.getUserInstanceByResultSet(resultSet);
                    } catch (SQLException e) {
                        System.out.println(String.format("Failed to work with result set (%s).", e.getMessage()));
                        e.printStackTrace();
                        return null;
                    }
                });
    }
}
