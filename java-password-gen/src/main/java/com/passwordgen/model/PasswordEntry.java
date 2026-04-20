package com.passwordgen.model;

import java.sql.Timestamp;

public class PasswordEntry {
    private int id;
    private String website;
    private String password;
    private Timestamp createdAt;

    public PasswordEntry() {
    }

    public PasswordEntry(String website, String password) {
        this.website = website;
        this.password = password;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
