package dao;

import dao.executor.Executor;
import model.UserProfile;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserProfileDao extends AbstractDao<UserProfile> {
    private Connection connection;
    
    private static final String SCHEMA_NAME = "\"stepic_jdbc\".\"user\"";
    private static final String INSERT_USER_TEMPLATE =
            "INSERT INTO " + SCHEMA_NAME + " (\"id\", \"login\", \"password\") VALUES (DEFAULT, ?, ?)";
    
    private static final String DELETE_USER_TEMPLATE =
            "DELETE FROM " + SCHEMA_NAME + " WHERE \"id\" = ? AND \"login\" LIKE ? ESCAPE '#' AND ctid = '(0,?)'";
    
    private static final String SELECT_ALL_USERS_TEMPLATE =
            "SELECT t.* FROM " + SCHEMA_NAME + " t %s";
    
    public UserProfileDao(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public boolean add(UserProfile userProfile) {
        try {
            return Executor.execQuery(connection,
                    new AbstractMap.SimpleEntry<>(
                            INSERT_USER_TEMPLATE,
                            new String[] {userProfile.getLogin(), userProfile.getPassword()}));
        }
        catch (SQLException e) {
            System.out.println(String.format("Failed to add user \"%s\" (%s).", userProfile, e.getMessage()));
            return false;
        }
    }
    
    @Override
    public boolean remove(UserProfile userProfile) {
        try {
            return Executor.execQuery(connection,
                    new AbstractMap.SimpleEntry<>(
                            DELETE_USER_TEMPLATE,
                            new String[] {
                                    String.valueOf(userProfile.getId()),
                                    userProfile.getLogin(),
                                    String.valueOf(userProfile.getId())
                            }
                    ));
        }
        catch (SQLException e) {
            System.out.println(String.format("Failed to delete user \"%s\" (%s).", userProfile, e.getMessage()));
            return false;
        }
    }
    
    @Override
    public UserProfile get(UserProfile userProfile) {
        List<UserProfile> list = getAllWhere(new QueryPair("login=?", userProfile.getLogin()));
        // If list is empty
        if (list.equals(Collections.<UserProfile>emptyList())) {
            return null;
        }
        return list.get(0);
    }
    public UserProfile getByLogin(String login) {
        return get(new UserProfile(0, login, null));
    }
    
    @Override
    public List<UserProfile> getAll() {
        return getAllWhere(EMPTY_QUERY_PAIR_ARRAY);
    }
    
    @Override
    public List<UserProfile> getAllWhere(QueryPair... conditions) {
        try {
            String queryCondStr = "";
            // Сonstant to save memory
            String[] parameters = EMTY_STRING_ARRAY;
            // If we have some conditions
            if (!Arrays.equals(conditions, EMPTY_QUERY_PAIR_ARRAY)) {
                // Collect all params to array and all string query to one string
                StringBuilder sb = new StringBuilder();
                parameters = new String[conditions.length];
                String prefix = "";
                sb.append("WHERE ");
                
                for (int i = 0; i < conditions.length; i++) {
                    sb.append(prefix);
                    sb.append(conditions[i].getQuery());
                    parameters[i] = conditions[i].getParam();
                    // If we have to or more item change prefix
                    if (prefix.equals("")) {
                        prefix = " and ";
                    }
                }
                queryCondStr = sb.toString();
            }
            
            return Executor.execQuery(connection,
                new AbstractMap.SimpleEntry<>(
                    String.format(SELECT_ALL_USERS_TEMPLATE, queryCondStr),
                    parameters
                ),
                rs -> {
                    try {
                        List<UserProfile> list = new ArrayList<>();
                        while (rs.next()) {
                            list.add(new UserProfile(
                                rs.getLong("id"),
                                rs.getString("login"),
                                rs.getString("password")));
                        }
                        return list;
                    }
                    catch (SQLException e) {
                        System.out.println(String.format("Failed to work with result set (%s).", e.getMessage()));
                        return Collections.emptyList();
                    }
                });
        }
        catch (SQLException e) {
            System.out.println(String.format("Failed to select user (%s).", e.getMessage()));
            return Collections.emptyList();
        }
    }
}
