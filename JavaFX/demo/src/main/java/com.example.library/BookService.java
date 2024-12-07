package com.example.library;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BookService {

    private static final String BASE_URL = "http://localhost:8080/lib/books"; // Backend URL

    private final HttpClient client;

    public BookService() {
        this.client = HttpClient.newHttpClient();
    }

    // Fetch all books from backend
    public String fetchAllBooks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/user/all")) // Use the correct endpoint for fetching books
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
            String requestBody = String.format(
                    "{\"title\": \"%s\"}", // Send the title of the book
                    bookTitle
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/borrow")) // Endpoint for borrowing a book
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 201; // Check if the status code is 201 (created)
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add a new book (used by frontend to send book details)
    public boolean addBook(String title, String author) {
        try {
            String requestBody = "{\"title\": \"" + title + "\", \"author\": \"" + author + "\", \"isbn\": 1234567890, \"available_copies\": 10}";

            // Assuming you need an Authorization header (e.g., Bearer token)
            String token = "your-auth-token-here";  // Replace with your actual token
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/admin/addbook"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)  // Add token if required
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log the response for debugging
            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            // Check if the status code is 201 (Created)
            return response.statusCode() == 201;  // Success
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
