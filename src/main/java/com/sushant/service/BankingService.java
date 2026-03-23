package src.main.java.com.sushant.service;

import java.util.HashMap;

public class BankingService {

    // Stores userId -> balance
    HashMap<String, Double> accounts = new HashMap<>();

    // Bug 1: No null check — NullPointerException risk (CRITICAL)
    public double getBalance(String userId) {
        return accounts.get(userId); // crashes if userId doesn't exist
    }

    // Bug 2: No synchronization — Race condition in multi-threading (CRITICAL)
    public void transfer(String fromUser, String toUser, double amount) {
        double fromBalance = accounts.get(fromUser);
        double toBalance = accounts.get(toUser);

        accounts.put(fromUser, fromBalance - amount);
        accounts.put(toUser, toBalance + amount);
    }

    // Bug 3: Negative amount allowed — Business logic bug (MAJOR)
    public void deposit(String userId, double amount) {
        double current = accounts.get(userId);
        accounts.put(userId, current + amount);
        // Nothing stops deposit(-5000) !
    }

    // Bug 4: Integer overflow (CRITICAL)
    public long calculateInterest(int principal, int rate, int years) {
        return principal * rate * years; // overflows if numbers are large!
    }

    // Bug 5: Sensitive data exposed in logs (BLOCKER)
    public void login(String username, String password) {
        System.out.println("Login attempt: " + username + " password: " + password);
        // password printed in plain text in logs!
    }

    // Bug 6: Resource never closed (CRITICAL)
    public void exportReport() throws Exception {
        java.io.FileWriter fw = new java.io.FileWriter("report.txt");
        fw.write("Banking Report");
        // fw.close() never called — file stays locked!
    }

    // Bug 7: Catch is too broad — hides real errors (MAJOR)
    public void processPayment(String userId, double amount) {
        try {
            deposit(userId, amount);
        } catch (Exception e) {
            // Catches EVERYTHING — even OutOfMemoryError!
            System.out.println("Something went wrong");
        }
    }
}