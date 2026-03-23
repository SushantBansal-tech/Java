import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserService {

    private static String DB_URL = "jdbc:mysql://localhost/mydb";  // Bug 1
    private static String PASSWORD = "admin123";                    // Bug 2

    public String getUserById(String userId) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, "root", PASSWORD);
            Statement stmt = conn.createStatement();

            // Bug 3: SQL Injection
            String query = "SELECT * FROM users WHERE id = '" + userId + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return rs.getString("username");
            }

        } catch (Exception e) {
            // Bug 4: Empty catch block - swallows the exception silently
        } finally {
            // Bug 5: Connection never closed — resource leak
        }

        return null;
    }

    public boolean isAdmin(String role) {
        // Bug 6: Comparing strings with == instead of .equals()
        if (role == "admin") {
            return true;
        }
        return false;
    }

    public int divide(int a, int b) {
        // Bug 7: No check for division by zero
        return a / b;
    }
}