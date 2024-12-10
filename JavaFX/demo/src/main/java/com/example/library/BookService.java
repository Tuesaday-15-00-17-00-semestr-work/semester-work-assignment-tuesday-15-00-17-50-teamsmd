package com.example.library;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    private static final String BASE_URL = "http://localhost:8080"; // Backend URL
    private final HttpClient client;

    public BookService() {
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Fetch all books from the backend.
     *
     * @return A list of BookEntity objects representing all books.
     */
    public List<BookEntity> fetchAllBooks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/lib/books/user/all"))
                    .header("Authorization", "Bearer " + AuthService.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println("Response from backend: " + responseBody);

            List<BookEntity> books = new ArrayList<>();
            if (responseBody.startsWith("[") && responseBody.endsWith("]")) {
                String[] bookArray = responseBody.substring(1, responseBody.length() - 1).split("\\},\\{");
                for (String book : bookArray) {
                    book = book.replace("{", "").replace("}", "");
                    String[] attributes = book.split(",");

                    int id = 0;
                    String title = null, author = null;
                    int availableCopies = 0;

                    for (String attribute : attributes) {
                        String[] keyValue = attribute.split(":");
                        String key = keyValue[0].replace("\"", "").trim();
                        String value = keyValue[1].replace("\"", "").trim();

                        switch (key) {
                            case "book_id":
                                id = Integer.parseInt(value);
                                break;
                            case "title":
                                title = value;
                                break;
                            case "author":
                                author = value;
                                break;
                            case "available_copies":
                                availableCopies = Integer.parseInt(value);
                                break;
                        }
                    }
                    if (id != 0 && title != null && author != null) {
                        books.add(new BookEntity(id, title, author, availableCopies));
                    }
                }
            }
            return books;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Borrow a book by user ID and book ID.
     *
     * @param userId  ID of the user borrowing the book.
     * @param bookId  ID of the book to borrow.
     * @return True if borrowing is successful, false otherwise.
     */
    public boolean borrowBook(int userId, int bookId) {
        try {
            String requestBody = String.format(
                    "{\"user_id\": %d, \"book_id\": %d, \"action\": \"borrow\", \"date\": \"%s\"}",
                    userId, bookId, java.time.LocalDateTime.now().toString()
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/lib/transactions/user/addtrans"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AuthService.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 || response.statusCode() == 201;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetch borrowed books from the backend.
     *
     * @return A list of BookEntity objects representing borrowed books.
     */
    public List<BookEntity> fetchBorrowedBooks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/lib/transactions/user/borrowed"))
                    .header("Authorization", "Bearer " + AuthService.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println("Borrowed Books Response: " + responseBody);

            List<BookEntity> books = new ArrayList<>();
            if (responseBody.startsWith("[") && responseBody.endsWith("]")) {
                String[] bookArray = responseBody.substring(1, responseBody.length() - 1).split("\\},\\{");
                for (String book : bookArray) {
                    book = book.replace("{", "").replace("}", "");
                    String[] attributes = book.split(",");

                    int id = 0;
                    String title = null, author = null;

                    for (String attribute : attributes) {
                        String[] keyValue = attribute.split(":");
                        String key = keyValue[0].replace("\"", "").trim();
                        String value = keyValue[1].replace("\"", "").trim();

                        switch (key) {
                            case "book_id":
                                id = Integer.parseInt(value);
                                break;
                            case "title":
                                title = value;
                                break;
                            case "author":
                                author = value;
                                break;
                        }
                    }
                    if (id != 0 && title != null && author != null) {
                        books.add(new BookEntity(id, title, author, 0));
                    }
                }
            }
            return books;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Return a borrowed book by its ID.
     *
     * @param bookId ID of the book to return.
     * @return True if returning is successful, false otherwise.
     */
    public boolean returnBook(int bookId) {
        try {
            String requestBody = String.format("{\"book_id\": %d}", bookId);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/lib/transactions/user/return"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AuthService.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
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
                    .uri(URI.create(BASE_URL + "/lib/books/user/addbook"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AuthService.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 201;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
