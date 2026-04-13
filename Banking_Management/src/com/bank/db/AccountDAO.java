package com.bank.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDAO {
    public void insertAccount(String accountNumber, String customerId, double balance, String accountType, double interestRate) {
        String query = "INSERT INTO accounts (account_number, customer_id, balance, account_type, interest_rate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, customerId);
            pstmt.setDouble(3, balance);
            pstmt.setString(4, accountType);
            pstmt.setDouble(5, interestRate);
            pstmt.executeUpdate();
            System.out.println("Account saved to database: " + accountNumber);
            
        } catch (SQLException e) {
            System.out.println("Error saving account:");
            e.printStackTrace();
        }
    }

    public void updateBalance(String accountNumber, double newBalance) {
        String query = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountNumber);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error updating account balance:");
            e.printStackTrace();
        }
    }
}
