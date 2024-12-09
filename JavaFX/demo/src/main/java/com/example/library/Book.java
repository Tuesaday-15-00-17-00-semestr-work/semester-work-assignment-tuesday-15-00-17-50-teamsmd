package com.example.library;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private int isbn;
    private int availableCopies;

    // Getters and setters for each field
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getIsbn() { return isbn; }
    public void setIsbn(int isbn) { this.isbn = isbn; }
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
}

