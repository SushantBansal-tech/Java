package com.sushant.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankingService {

    // Fix: proper logger
    private static final Logger logger =
        Logger.getLogger(BankingService.class.getName());

    HashMap<String, Double> accounts = new HashMap<>();

    // Fix 1: null check added
    public double getBalance(String userId) {
        if (!accounts.containsKey(userId)) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        return accounts.get(userId);
    }

    // Fix 2: synchronized block for thread safety
    public synchronized void transfer(String fromUser, String toUser, double amount) {
        double fromBalance = accounts.get(fromUser);
        double toBalance = accounts.get(toUser);
        accounts.put(fromUser, fromBalance - amount);
        accounts.put(toUser, toBalance + amount);
    }

    // Fix 3: negative amount check
    public void deposit(String userId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        double current = accounts.get(userId);
        accounts.put(userId, current + amount);
    }

    // Fix 4: cast to long before multiplication
    public long calculateInterest(int principal, int rate, int years) {
        return (long) principal * rate * years;
    }

    // Fix 5: never log passwords + use logger
    public void login(String username, String password) {
        logger.info("Login attempt: " + username);
        // password never logged!
    }

    // Fix 6: try-with-resources (auto closes file)
    // Fix 7: specific IOException instead of generic Exception
    public void exportReport() throws IOException {
        try (FileWriter fw = new FileWriter("report.txt")) {
            fw.write("Banking Report");
        }
    }

    // Fix 8: catch specific exception + use logger
    public void processPayment(String userId, double amount) {
        try {
            deposit(userId, amount);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Payment processing failed", e);
        }
    }
}