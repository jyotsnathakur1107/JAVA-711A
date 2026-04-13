package com.bank.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {
    public static void createTables() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("Cannot create tables: Database connection is null.");
            return;
        }

        try (Statement stmt = conn.createStatement()) {
            String createCustomersTable = "CREATE TABLE IF NOT EXISTS customers (" +
                    "customer_id VARCHAR(50) PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.executeUpdate(createCustomersTable);

            String createAccountsTable = "CREATE TABLE IF NOT EXISTS accounts (" +
                    "account_number VARCHAR(50) PRIMARY KEY," +
                    "customer_id VARCHAR(50)," +
                    "balance DOUBLE NOT NULL DEFAULT 0.0," +
                    "account_type VARCHAR(20)," +
                    "interest_rate DOUBLE," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE)";
            stmt.executeUpdate(createAccountsTable);

            String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                    "transaction_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "account_number VARCHAR(50)," +
                    "transaction_type VARCHAR(20) NOT NULL," +
                    "amount DOUBLE NOT NULL," +
                    "balance_after DOUBLE NOT NULL," +
                    "transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (account_number) REFERENCES accounts(account_number) ON DELETE CASCADE)";
            stmt.executeUpdate(createTransactionsTable);

            String createLoansTable = "CREATE TABLE IF NOT EXISTS loans (" +
                    "loan_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "customer_id VARCHAR(50)," +
                    "loan_amount DOUBLE NOT NULL," +
                    "interest_rate DOUBLE NOT NULL," +
                    "tenure INT NOT NULL," +
                    "emi DOUBLE," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE)";
            stmt.executeUpdate(createLoansTable);

            System.out.println("Database tables ensured to exist.");

        } catch (SQLException e) {
            System.out.println("Error creating tables:");
            e.printStackTrace();
        }
    }
}
