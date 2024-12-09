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

    /**
     * Fetch all books from the backend and print only the titles.
     *
     * @return A string representing the list of book titles.
     */
    public String fetchAllBooks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/user/all")) // Correct endpoint for fetching books
                    .header("Authorization", "Bearer " + AuthService.getToken()) // Pass token
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log response for debugging
            System.out.println("Fetch All Books - Response Code: " + response.statusCode());
            System.out.println("Fetch All Books - Response Body: " + response.body());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                // Extract and return only the book titles
                return extractAndPrintTitles(responseBody);
            } else {
                System.err.println("Failed to fetch books. Status code: " + response.statusCode());
                return "[]"; // Return empty JSON array in case of failure
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "[]"; // Return empty JSON array in case of an exception
        }
    }

    /**
     * Extracts book titles from the response body and returns them.
     *
     * @param responseBody The JSON string representing the list of books.
     * @return A string with the book titles.
     */
    private String extractAndPrintTitles(String responseBody) {
        StringBuilder titles = new StringBuilder();

        // Parse the response to extract only the book titles
        responseBody = responseBody.substring(1, responseBody.length() - 1);  // Remove the surrounding [ ] brackets
        String[] books = responseBody.split("\\},\\{");

        // Loop through each book and extract the title
        for (String book : books) {
            String title = extractTitleFromBook(book);
            titles.append(title).append("\n"); // Append the book title
        }

        return titles.toString().trim(); // Return the formatted titles
    }

    /**
     * Extracts the title from a single book JSON string.
     *
     * @param book A string representing a single book in JSON format.
     * @return The title of the book.
     */
    private String extractTitleFromBook(String book) {
        // Find the part that contains the title
        int titleStartIndex = book.indexOf("\"title\":\"") + 9; // Skip the "title":" part
        int titleEndIndex = book.indexOf("\"", titleStartIndex); // Find the ending quote
        return book.substring(titleStartIndex, titleEndIndex); // Extract and return the title
    }

    /**
     * Borrow a book by title.
     *
     * @param bookTitle Title of the book to borrow.
     * @return True if borrowing is successful, false otherwise.
     */
    public boolean borrowBook(String bookTitle) {
        try {
            String requestBody = String.format("{\"title\": \"%s\"}", bookTitle);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/borrow")) // Correct endpoint for borrowing
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AuthService.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log response for debugging
            System.out.println("Borrow Book - Response Code: " + response.statusCode());
            System.out.println("Borrow Book - Response Body: " + response.body());

            return response.statusCode() == 200; // Check if the response code indicates success
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Add a new book to the library.
     *
     * @param title  Title of the book.
     * @param author Author of the book.
     * @return True if the book is added successfully, false otherwise.
     */
    public boolean addBook(String title, String author) {
        try {
            String requestBody = String.format(
                    "{\"title\": \"%s\", \"author\": \"%s\", \"isbn\": 0, \"available_copies\": 1}",
                    title, author
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/admin/addbook")) // Correct endpoint for adding books
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AuthService.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log response for debugging
            System.out.println("Add Book - Response Code: " + response.statusCode());
            System.out.println("Add Book - Response Body: " + response.body());

            return response.statusCode() == 201; // Return true if the book was created successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetch borrowed books from the backend.
     *
     * @return JSON string representing the list of borrowed books.
     */
    public String fetchBorrowedBooks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/borrowed")) // Replace with the correct endpoint for borrowed books
                    .header("Authorization", "Bearer " + AuthService.getToken()) // Pass token
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log response for debugging
            System.out.println("Fetch Borrowed Books - Response Code: " + response.statusCode());
            System.out.println("Fetch Borrowed Books - Response Body: " + response.body());

            if (response.statusCode() == 200) {
                return response.body(); // Return the list of borrowed books if the response is OK
            } else {
                System.err.println("Failed to fetch borrowed books. Status code: " + response.statusCode());
                return "[]"; // Return empty JSON array in case of failure
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "[]"; // Return empty JSON array in case of an exception
        }
    }
}
