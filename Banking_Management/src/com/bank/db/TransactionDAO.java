package com.bank.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {
    public void insertTransaction(String accountNumber, String transactionType, double amount, double balanceAfter) {
        String query = "INSERT INTO transactions (account_number, transaction_type, amount, balance_after) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, transactionType);
            pstmt.setDouble(3, amount);
            pstmt.setDouble(4, balanceAfter);
            pstmt.executeUpdate();
            System.out.println("Transaction recorded in database: " + transactionType + " of " + amount);
            
        } catch (SQLException e) {
            System.out.println("Error saving transaction:");
            e.printStackTrace();
        }
    }
}
