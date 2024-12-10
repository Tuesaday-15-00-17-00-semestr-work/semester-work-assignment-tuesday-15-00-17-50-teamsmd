package com.example.library;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TransactionService {

    private static final String BASE_URL = "http://localhost:8080/lib/transactions";

    private final HttpClient client;

    public TransactionService() {
        this.client = HttpClient.newHttpClient();
    }

    // Fetch all transactions
    public String fetchAllTransactions() {
    	
        try {
        	
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL+"/user/all"))
                    .header("Authorization", "Bearer " + AuthService.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "[]"; // Return empty list in case of failure
        }
    }

    // Record a transaction (borrow/return)
    public boolean recordTransaction(String bookTitle, String username, String action) {
        try {
            String requestBody = String.format(
                    "{\"bookTitle\": \"%s\", \"username\": \"%s\", \"action\": \"%s\", \"date\": \"10.12.2024\"}",
                    bookTitle, username, action
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Authorization", "Bearer " + AuthService.getToken())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 201; // HTTP 201 Created
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
