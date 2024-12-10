package com.example.library;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final String BASE_URL = "http://localhost:8080/admin/users"; // Endpoint for user-related actions
    private final HttpClient client;

    public UserService() {
        this.client = HttpClient.newHttpClient();
    }

    // Fetch all users
    public List<UserEntity> fetchAllUsers() {
        try {
            // Get the token from AuthService (assuming it has been set after login)
            String token = "Bearer " + AuthService.getToken();  // Get the token here

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/all"))
                    .GET()
                    .header("Authorization", token)  // Add the Authorization header with the token
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println("Response from backend: " + responseBody); // Debugging line

            // Create a list to hold the UserEntity objects
            List<UserEntity> users = new ArrayList<>();

            // Ensure the response is a valid JSON array
            if (responseBody.startsWith("[") && responseBody.endsWith("]")) {
                // Remove outer brackets and split by "},{"
                String[] userStrings = responseBody.substring(1, responseBody.length() - 1).split("\\},\\{");

                for (String userString : userStrings) {
                    userString = userString.replace("{", "").replace("}", "");  // Clean up the string

                    // Split user attributes
                    String[] userDetails = userString.split(",");

                    String username = null, email = null, role = null;
                    int id = 0;

                    // Extract individual fields
                    for (String detail : userDetails) {
                        String[] keyValue = detail.split(":");
                        if (keyValue[0].contains("user_id")) {
                            id = Integer.parseInt(keyValue[1].replace("\"", "").trim());
                        } else if (keyValue[0].contains("username")) {
                            username = keyValue[1].replace("\"", "").trim();
                        } else if (keyValue[0].contains("email")) {
                            email = keyValue[1].replace("\"", "").trim();
                        } else if (keyValue[0].contains("role")) {
                            role = keyValue[1].replace("\"", "").trim();
                        }
                    }

                    // Create UserEntity objects, now including id
                    if (username != null && email != null && role != null) {
                        users.add(new UserEntity(id, username, email, role));
                    }
                }
            }

            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();  // Return an empty list in case of failure
        }
    }

    // Register a new user (this is used for regular user registration)
    public boolean registerUser(String username, String password, String email) {
        try {
            String requestBody = String.format(
                    "{\"username\": \"%s\", \"password\": \"%s\", \"email\": \"%s\"}",
                    username, password, email
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/register")) // Adjust endpoint for registration
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

    // Delete a user (admin functionality)
    public boolean deleteUser(int userId) {
        try {
            String requestBody = String.format("{\"id\": %d}", userId);

            // Correct DELETE request without a body
            String token = "Bearer " + AuthService.getToken();  // Use the token for auth

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/delete"))
                    .DELETE()
                    .header("Content-Type", "application/json")
                    .header("Authorization", token) // Include token in the header
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200; // HTTP 200 OK if deletion is successful
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
