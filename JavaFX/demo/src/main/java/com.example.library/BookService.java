package com.example.library;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class BookService {

    private static final String BASE_URL = "http://localhost:8080"; // Backend URL

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
                    .uri(URI.create(BASE_URL + "/lib/books/user/all")) // Correct endpoint for fetching books
                    .header("Authorization", "Bearer " + AuthService.getToken()) // Pass token
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body(); // Return the raw response if successful
            } else {
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

        responseBody = responseBody.substring(1, responseBody.length() - 1);  // Remove the surrounding [ ] brackets
        String[] books = responseBody.split("\\},\\{");

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
        String titleKey = "\"title\":";
        int titleIndex = book.indexOf(titleKey);
        if (titleIndex != -1) {
            int start = titleIndex + titleKey.length() + 1;
            int end = book.indexOf("\"", start);
            String title = book.substring(start, end);
            System.out.println("Extracted title: " + title);  // Debug print
            return title;
        }
        return "";
    }



    /**
     * Borrow a book by title.
     *
     * @param bookTitle Title of the book to borrow.
     * @return True if borrowing is successful, false otherwise.
     */
    public boolean borrowBook(int userId, int bookId, String bookTitle) {
        try {
            String requestBody = String.format("{\"userId\": %d, \"bookId\": %d, \"bookTitle\": \"%s\"}", userId, bookId, bookTitle);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/user/borrow")) // Correct endpoint for borrowing a book
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AuthService.getToken()) // Include token
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200; // Check if the response code indicates success
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Return a borrowed book by title.
     *
     * @param bookTitle Title of the book to return.
     * @return True if returning is successful, false otherwise.
     */
    public boolean returnBook(String bookTitle) {
        try {
            String requestBody = String.format("{\"title\": \"%s\"}", bookTitle);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/return")) // Correct endpoint for returning books
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AuthService.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

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

            return response.statusCode() == 201; // Return true if the book was created successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetch borrowed books from the backend for the current user.
     *
     * @return JSON string representing the list of borrowed books.
     */
    public String fetchBorrowedBooks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/user/borrowed")) // Correct endpoint for fetching borrowed books
                    .header("Authorization", "Bearer " + AuthService.getToken()) // Pass token
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

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
