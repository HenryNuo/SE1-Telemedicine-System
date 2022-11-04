package com.hamilton.daotests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestHelper {
    public static String javaDatabaseURL = "jdbc:mysql://localhost:3306/tests?serverTimezone=UTC";
    public static String javaDatabaseUsername = "root";
    public static String javaDatabasePassword = "Coffee54853";
 //   public static String javaDatabasePassword = "My_SQL_Server1";
    public static String javaDatabaseDriver = "com.mysql.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(javaDatabaseDriver);
            connection = DriverManager.getConnection(javaDatabaseURL, javaDatabaseUsername, javaDatabasePassword);
            System.out.println(connection);
        } catch (SQLException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public static void disableForeignKeys(){
        String statements = "SET FOREIGN_KEY_CHECKS = 0";
        try (PreparedStatement statement = getConnection().prepareStatement(statements)){
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
