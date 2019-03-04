package dao.executor;

import utils.UserUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Function;

public class Executor {
    // Some decoration for sql string execution (map where k - query string, v - array of string params)
    public static <T> T execQuery(Connection connection, Map.Entry<String, Object[]> query, Function<ResultSet, T> resultsFunc) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query.getKey())) {
            // Safe params for sql query (protects against SQL-injection)
            if (query.getValue() != null) {
                for (int i = 0; i < query.getValue().length; i++) {
                    // try {
                        UserUtils.setStatementVlueByType(stmt, i + 1, query.getValue()[i]);
                    // } catch (Exception e) {
                    //     e.printStackTrace();
                    //     return resultsFunc.apply(null);
                    // }
                }
            }
            
            stmt.execute();
            
            // If update db
            String qrTmp = query.getKey().toLowerCase();
            if (qrTmp.contains("delete") || qrTmp.contains("insert")) {
                return resultsFunc.apply(null);
            }
            
            try (ResultSet result = stmt.getResultSet()) {
                return resultsFunc.apply(result);
            }
        }
    }
    
    public static Boolean execQuery(Connection connection, Map.Entry<String, Object[]> query) throws SQLException {
        // If query execute fine, that return true
        return execQuery(connection, query, rs -> true);
    }
}
