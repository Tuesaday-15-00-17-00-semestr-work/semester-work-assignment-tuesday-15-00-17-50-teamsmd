package com.example.library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionManager {

    private List<Transaction> transactions;  // Store transactions

    public TransactionManager() {
        this.transactions = new ArrayList<>();
    }

    // Add a transaction (Borrow or Return)
    public void addTransaction(String bookTitle, String username, String action) {
        String transactionId = UUID.randomUUID().toString();  // Generate unique ID
        Transaction transaction = new Transaction(transactionId, bookTitle, username, action, LocalDate.now());
        transactions.add(transaction);
        System.out.println("Transaction added: " + transaction);
    }

    // Get all transactions
    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Print all transactions to the console
    public void printAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}
