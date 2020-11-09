package DataBaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {


    public static Statement statement;
    public static Connection connection;

    public static void createConnection(String url,String userName, String password){
        try {
            connection= DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement=connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
