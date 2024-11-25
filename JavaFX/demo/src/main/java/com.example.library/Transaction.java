package com.example.library;

import java.time.LocalDate;

public class Transaction {
    private String transactionId;
    private String bookTitle;
    private String username;
    private String action; // Borrow or Return
    private LocalDate date;

    public Transaction(String transactionId, String bookTitle, String username, String action, LocalDate date) {
        this.transactionId = transactionId;
        this.bookTitle = bookTitle;
        this.username = username;
        this.action = action;
        this.date = date;
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", username='" + username + '\'' +
                ", action='" + action + '\'' +
                ", date=" + date +
                '}';
    }
}
