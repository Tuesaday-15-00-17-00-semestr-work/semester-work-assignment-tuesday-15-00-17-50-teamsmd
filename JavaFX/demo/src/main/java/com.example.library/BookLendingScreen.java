package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookLendingScreen {

    private MainApp mainApp;
    private BookService bookService = new BookService(); // Use BookService for API calls

    public BookLendingScreen(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void start(Stage stage) {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Available Books for Lending");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ListView<String> bookListView = new ListView<>();
        Button borrowButton = new Button("Borrow");
        Button refreshButton = new Button("Refresh");
        Button addBookButton = new Button("Add New Book");

        // Fetch available books
        fetchAvailableBooks(bookListView);

        // Borrow action
        borrowButton.setOnAction(e -> {
            String selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                int userId = 1; // Replace with the actual user ID (get from session or login)

                // Extract the book ID and title from selected book
                int bookId = extractBookIdFromBook(selectedBook);

                boolean success = bookService.borrowBook(userId, bookId, selectedBook);
                if (success) {
                    bookListView.getItems().remove(selectedBook);
                    showSuccess("You have borrowed \"" + selectedBook + "\"");
                } else {
                    showError("Failed to borrow book. Please try again.");
                }
            } else {
                showError("Please select a book to borrow!");
            }
        });

        // Refresh action
        refreshButton.setOnAction(e -> fetchAvailableBooks(bookListView));

        // Add new book action
        addBookButton.setOnAction(e -> showAddBookPopup(stage));

        layout.getChildren().addAll(titleLabel, bookListView, borrowButton, refreshButton, addBookButton);
        mainApp.getBorderPane().setCenter(layout);
    }

    // Fetch available books from backend and return a ListView of titles
    private void fetchAvailableBooks(ListView<String> bookListView) {
        String response = bookService.fetchAllBooks(); // Fetch all books
        bookListView.getItems().clear();
        System.out.println("Response from backend: " + response); // Check the full response

        if (!response.equals("[]")) {
            // Manually parse the JSON response without external libraries
            String[] books = response.split("\\},\\{"); // Split books by the JSON object separator
            for (String book : books) {
                String title = extractTitleFromBook(book);
                int bookId = extractBookIdFromBook(book);

                // Debug print statements to check extracted title and book ID
                System.out.println("Extracted title: " + title);
                System.out.println("Extracted book ID: " + bookId);

                // Only add to the list if title is not empty
                if (!title.isEmpty() && bookId != 0) {
                    bookListView.getItems().add(title); // Display the book title in the list
                } else {
                    System.out.println("Skipping invalid book with ID: " + bookId + " and Title: " + title);
                }
            }
        } else {
            showError("Failed to fetch books or no books available.");
        }
    }

    private int extractBookIdFromBook(String book) {
        String idKey = "\"book_id\":";
        int idIndex = book.indexOf(idKey);
        if (idIndex != -1) {
            int start = idIndex + idKey.length();
            int end = book.indexOf(",", start);
            try {
                int bookId = Integer.parseInt(book.substring(start, end).trim());
                System.out.println("Extracted book ID: " + bookId);  // Debug print
                return bookId;
            } catch (NumberFormatException e) {
                System.err.println("Error parsing book ID from: " + book);
            }
        }
        return 0; // Default ID if not found
    }

    private String extractTitleFromBook(String book) {
        String titleKey = "\"title\":";
        int titleIndex = book.indexOf(titleKey);
        if (titleIndex != -1) {
            int start = titleIndex + titleKey.length() + 1; // Skip the `"` after the `:`
            int end = book.indexOf("\"", start);
            String title = book.substring(start, end);
            System.out.println("Extracted title: " + title);  // Debug print
            return title;
        }
        return ""; // Return empty if no title found
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAddBookPopup(Stage stage) {
        // Open the AddBookPopup to add a new book to the system
        AddBookPopup addBookPopup = new AddBookPopup(mainApp);
        addBookPopup.showPopup(stage);
    }
}
