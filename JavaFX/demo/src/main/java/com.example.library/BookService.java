package com.example.library;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BookService {

    private static final String BASE_URL = "http://localhost:8080/lib/books";  // Backend URL

    private final HttpClient client;

    public BookService() {
        this.client = HttpClient.newHttpClient();
    }

    // Fetch all books from backend
    public String fetchAllBooks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/user/all"))  // Use the correct endpoint for fetching books
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
                    "{\"title\": \"%s\"}",  // Send the title of the book
                    bookTitle
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/borrow"))  // Endpoint for borrowing a book
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 201;  // Check if the status code is 201 (created)
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addBook(String title, String author) {
        try {
            String requestBody = String.format(
                    "{\"title\": \"%s\", \"author\": \"%s\", \"isbn\": 0, \"available_copies\": 1}",
                    title, author
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/admin/addbook"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log the response for debugging
            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            // Check if the status code is 201 (Created)
            if (response.statusCode() == 201) {
                return true;  // Success
            } else {
                // If it's not 201, show the error message
                System.err.println("Error: " + response.body());
                return false;  // Failure
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
