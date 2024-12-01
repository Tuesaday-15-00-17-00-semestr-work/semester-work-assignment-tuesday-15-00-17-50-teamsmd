package com.example.library;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BookService {

    private static final String BASE_URL = "http://localhost:8080/lib/books";

    private final HttpClient client;

    public BookService() {
        this.client = HttpClient.newHttpClient();
    }

    // Fetch all books
    public String fetchAllBooks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "[]"; // Return empty list in case of failure
        }
    }

    // Borrow a book
    public boolean borrowBook(String bookTitle) {
        try {
            String requestBody = "{\"title\": \"" + bookTitle + "\"}";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 201; // HTTP 201 Created
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
