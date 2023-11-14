package poly.edu.untinity;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    public static String USER_NAME = "sa";
    public static String PASSWORD_NAME = "1";
    public static String url = "jdbc:sqlserver://localhost:1433;databaseName=EduSys;encrypt=true;trustServerCertificate=true;";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, USER_NAME, PASSWORD_NAME);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Thành công nhé");
        }else{
            System.out.println("Thất bại rồi");
        }

    }
}
