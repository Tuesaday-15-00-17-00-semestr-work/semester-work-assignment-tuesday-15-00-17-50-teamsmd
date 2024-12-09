package com.example.library;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserService {

    private static final String BASE_URL = "http://localhost:8080/admin/users";

    private final HttpClient client;

    public UserService() {
        this.client = HttpClient.newHttpClient();
    }

    // Fetch all users
    public String fetchAllUsers() {
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

    // Register a new user
    public boolean registerUser(String username, String password, String email) {
        try {
            String requestBody = String.format(
                    "{\"username\": \"%s\", \"password\": \"%s\", \"email\": \"%s\"}",
                    username, password, email
            );

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
