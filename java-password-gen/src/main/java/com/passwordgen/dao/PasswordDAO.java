package com.passwordgen.dao;

import com.passwordgen.model.PasswordEntry;
import com.passwordgen.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordDAO {

    public void savePassword(PasswordEntry entry) {
        String sql = "INSERT INTO passwords (website, password) VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entry.getWebsite());
            pstmt.setString(2, entry.getPassword());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PasswordEntry> getAllPasswords() {
        List<PasswordEntry> passwords = new ArrayList<>();
        String sql = "SELECT * FROM passwords ORDER BY created_at DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PasswordEntry entry = new PasswordEntry();
                entry.setId(rs.getInt("id"));
                entry.setWebsite(rs.getString("website"));
                entry.setPassword(rs.getString("password"));
                entry.setCreatedAt(rs.getTimestamp("created_at"));
                passwords.add(entry);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passwords;
    }
}
