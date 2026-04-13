package com.bank.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/banking_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Ayush@123";

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to MySQL Database.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Please add the mysql-connector-j JAR to your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database. Ensure MySQL is running and the database 'banking_db' exists.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
