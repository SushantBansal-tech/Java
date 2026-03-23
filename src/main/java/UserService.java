public class UserService {

    // Bug 1: Hardcoded password (BLOCKER)
    private static String PASSWORD = "admin123";

    public String getUserById(String userId) {
        try {
            // Bug 2: SQL Injection (CRITICAL)
            String query = "SELECT * FROM users WHERE id = '" + userId + "'";
            System.out.println(query);

        } catch (Exception e) {
            // Bug 3: Empty catch block (MAJOR)
        }

        return null;
    }

    public boolean isAdmin(String role) {
        // Bug 4: String compared with == instead of .equals() (CRITICAL)
        if (role == "admin") {
            return true;
        }
        return false;
    }

    public int divide(int a, int b) {
        // Bug 5: Division by zero risk (CRITICAL)
        return a / b;
    }
}