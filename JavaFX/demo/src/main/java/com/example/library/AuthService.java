package com.example.library;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {

    private static final String BASE_URL = "http://localhost:8080/auth"; // Backend URL

    private HttpClient client = HttpClient.newHttpClient();
    private static String token;  // Store the token for authentication

    // Get the token (to be used for future requests)
    public static String getToken() {
        return token;
    }

    // Register method (automatically set role to 2 for "user")
    public String register(String username, String password, String name, String email) {
        try {
            String json = "{"
                    + "\"username\":\"" + username + "\","
                    + "\"password\":\"" + password + "\","
                    + "\"name\":\"" + name + "\","
                    + "\"role\":\"2\","  // Automatically set role to 2 (user)
                    + "\"email\":\"" + email + "\""
                    + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return "User registered!";
            } else {
                return response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Registration failed!";
        }
    }

    // Login method
    public String login(String username, String password) {
        try {
            // Log the request
            System.out.println("Sending login request with username: " + username + " and password: " + password);

            String json = "{"
                    + "\"username\":\"" + username + "\","
                    + "\"password\":\"" + password + "\""
                    + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log the response status and body for debugging
            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            if (response.statusCode() == 200) {
                // Assuming the response body contains the token
                token = response.body();  // Store the token
                System.out.println("Stored token: " + AuthService.getToken());
                return "User logged in!";
            } else {
                return response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Login failed!";
        }
    }
}