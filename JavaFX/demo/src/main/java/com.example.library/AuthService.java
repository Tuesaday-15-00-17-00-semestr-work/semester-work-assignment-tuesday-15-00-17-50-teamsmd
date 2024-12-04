package com.example.library;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private static final String BASE_URL = "http://localhost:8080/auth"; // Backend URL

    // HttpClient to send HTTP requests
    private HttpClient client = HttpClient.newHttpClient();

    // Register method (automatically set role to 1 for "user")
    public String register(String username, String password, String name, String email) {
        try {
            // Manually build the JSON payload
            String json = "{"
                    + "\"username\":\"" + username + "\","
                    + "\"password\":\"" + password + "\","
                    + "\"name\":\"" + name + "\","
                    + "\"role\":\"1\","  // Automatically set role to 1 (user)
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

    // Login method (unchanged)
    public String login(String username, String password) {
        try {
            // Manually build the JSON payload
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
            if (response.statusCode() == 200) {
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
