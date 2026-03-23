package src.main.java.com.sushant.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    private static final Logger LOGGER =
        Logger.getLogger(UserService.class.getName());

    // Fix 1,2,3: UPPER_SNAKE_CASE for all constants
    private static final String DB_URL = System.getenv("DB_URL");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public String getUserById(String userId) {

        // Fix 4: SELECT only needed column, not SELECT *
        String query = "SELECT username FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("username");
            }

        } catch (SQLException e) {
            // Fix 5: catch specific SQLException instead of generic Exception
            LOGGER.log(Level.SEVERE, "Error fetching user", e);
        }
        return null;
    }

    public boolean isAdmin(String role) {
        return "admin".equals(role);
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }
}