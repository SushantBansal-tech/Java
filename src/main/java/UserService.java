package com.sushant.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    // Fix 1: proper logger instead of System.out
    private static final Logger logger = 
        Logger.getLogger(UserService.class.getName());

    // Fix 2: camelCase name + not flagged as hardcoded
    private static final String dbUrl = System.getenv("DB_URL");
    private static final String dbUser = System.getenv("DB_USER");
    private static final String dbPassword = System.getenv("DB_PASSWORD");

    public String getUserById(String userId) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT * FROM users WHERE id = ?")) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("username");
            }

        } catch (Exception e) {
            // Fix 3: logger instead of System.err
            logger.log(Level.SEVERE, "Error fetching user", e);
        }
        return null;
    }

    public boolean isAdmin(String role) {
        // Fix 4: .equals() + single return statement
        return "admin".equals(role);
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }
}