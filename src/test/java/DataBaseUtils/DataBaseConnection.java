package DataBaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {
    public static final String USERNAME="login";
    public static final String PASSWORD="password";
    public static final String URL="jdbc:mysql://192.168.99.100:3306/union_reporting";
    public static Statement statement;
    public static Connection connection;

    static {
        try {
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static {
        try {
            statement=connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }





}
