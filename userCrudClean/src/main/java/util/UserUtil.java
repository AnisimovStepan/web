package util;

import model.RoleType;
import model.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserUtil {
    public static User getUserInstanceByParams(HttpServletRequest req) {
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setRole(RoleType.valueOf(req.getParameter("role")));
        user.setFirstName(req.getParameter("first-name"));
        user.setLastName(req.getParameter("last-name"));
        user.setMiddleName(req.getParameter("middle-name"));
        return user;
        
    }
    public static User getUserInstanceByResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(RoleType.valueOf(resultSet.getString("role")));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setMiddleName(resultSet.getString("middle_name"));
        return user;
    }
    
    // Check user fields and send error to request attr
    public static boolean invalidUserFields(User user, HttpServletRequest req) {
        // Send error message
        String emptyStr = "";
        if ((user.getLogin() == null) || (user.getFirstName() == null) || (user.getLastName() == null) ||
                (user.getLogin().equals(emptyStr)) || (user.getFirstName().equals(emptyStr)) ||
                (user.getPassword().equals(emptyStr)) || (user.getLastName().equals(emptyStr))) {
            
            req.setAttribute("errorMessage", "Login, password, first/last name is required.");
            req.setAttribute("user", user);
            return true;
        }
        else {
            return false;
        }
    }
    
    // Check user fields and send error to request attr
    public static void setStatementValueByType(PreparedStatement statement, int index, Object primitiveType) throws SQLException {
        // Types types = Types.getType(primitiveType.getClass().getDeclaredField("value").getType());
        if (primitiveType instanceof String) {
            statement.setString(index, (String) primitiveType);
        } else if (primitiveType instanceof Long){
            statement.setLong(index, (long) primitiveType);
        }
    }
}
