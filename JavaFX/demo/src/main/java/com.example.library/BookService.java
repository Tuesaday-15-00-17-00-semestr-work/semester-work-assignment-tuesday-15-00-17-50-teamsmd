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
                    .header("Authorization", "Bearer " + AuthService.getToken())  // Pass the token in header
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
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AuthService.getToken())  // Pass the token in header
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log the response status and body for debugging
            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            return response.statusCode() == 201; // Check if the status code is 201 (created)
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add a new book (used by frontend to send book details)
    public boolean addBook(String title, String author) {
        try {
            String token = AuthService.getToken();

            // Print the token to verify it
            System.out.println("Using token: " + token);

            String requestBody = String.format(
                    "{\"title\": \"%s\", \"author\": \"%s\", \"isbn\": 0, \"available_copies\": 1}",
                    title, author
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/admin/addbook"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AuthService.getToken())  // Pass the token in header
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Print the request headers for debugging
            System.out.println("Request Headers: " + request.headers());

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            return response.statusCode() == 201; // Return true if book added successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
