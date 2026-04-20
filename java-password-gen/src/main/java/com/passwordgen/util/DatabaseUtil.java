package com.passwordgen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    // IMPORTANT: Update these credentials if your local Postgres setup differs
    private static final String URL = "jdbc:postgresql://localhost:5432/passworddb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "jojo#2006";

    static {
        try {
            // Load PostgreSQL Driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("PostgreSQL JDBC Driver not found!");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
