package com.bank.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoanDAO {
    public void insertLoan(String customerId, double loanAmount, double interestRate, int tenure, double emi) {
        String query = "INSERT INTO loans (customer_id, loan_amount, interest_rate, tenure, emi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, customerId);
            pstmt.setDouble(2, loanAmount);
            pstmt.setDouble(3, interestRate);
            pstmt.setInt(4, tenure);
            pstmt.setDouble(5, emi);
            pstmt.executeUpdate();
            System.out.println("Loan saved to database for customer: " + customerId);
            
        } catch (SQLException e) {
            System.out.println("Error saving loan:");
            e.printStackTrace();
        }
    }
}
