package dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Herbert Seewann
 */
public class DBManager {

    private DBPropertiesManager dbpm = DBPropertiesManager.getInstance();
    private Connection con;

    private DBManager() {
        try {
            dbpm.createProperties("D:\\Schule\\4. Jahrgang 2019 - 2020\\Programmieren\\Beispiele\\Servlets_JSP_Webshop\\src\\main\\java\\dbconfig\\postgres.db.properties");
            Class.forName(dbpm.getDriver());
            con = DriverManager.getConnection(dbpm.getUrl(),
                    dbpm.getUsername(),
                    dbpm.getPassword());
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static DBManager getInstance() {
        DBManager instance = DBManagerHolder.INSTANCE;
        return instance;
    }

    private static class DBManagerHolder {

        private static final DBManager INSTANCE = new DBManager();
    }
    
    public Statement createStatement() throws Exception {
        return con.createStatement();
    }

    public PreparedStatement createPreparedStatement(String sql, int... params) throws SQLException {
        switch (params.length) {
            case 0:
                return con.prepareStatement(sql);
            default:
                return con.prepareStatement(sql, params[0]);
}
    }

    public void close() throws SQLException {
        con.close();
    }

    public int readGeneratedKeys(Statement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            return id;
        }
        throw new SQLException("No generated KeyId");
    }
}