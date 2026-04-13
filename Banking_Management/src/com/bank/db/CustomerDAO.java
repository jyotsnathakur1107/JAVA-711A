package com.bank.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {
    public void insertCustomer(String customerId, String name) {
        String query = "INSERT INTO customers (customer_id, name) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, customerId);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println("Customer saved to database: " + customerId);
            
        } catch (SQLException e) {
            System.out.println("Error saving customer:");
            e.printStackTrace();
        }
    }

    public void deleteCustomer(String customerId) {
        String query = "DELETE FROM customers WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, customerId);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Customer permanently deleted from database: " + customerId);
            } else {
                System.out.println("No customer found with ID: " + customerId);
            }
            
        } catch (SQLException e) {
            System.out.println("Error deleting customer:");
            e.printStackTrace();
        }
    }
}
