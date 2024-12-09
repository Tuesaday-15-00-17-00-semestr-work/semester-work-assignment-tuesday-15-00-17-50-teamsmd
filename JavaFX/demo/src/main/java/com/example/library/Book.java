package com.example.library;

public class Book {
    private int id;
    private String title;

    // Constructor to initialize the Book object
    public Book(int id, String title) {
        this.id = id;
        this.title = title;
    }

    // Getters and Setters for id and title
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Override toString method to display the book title in the ListView
    @Override
    public String toString() {
        return title;
    }
}
