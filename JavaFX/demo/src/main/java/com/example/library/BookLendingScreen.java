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

    private MainApp mainApp;  // Reference to MainApp
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
                boolean success = bookService.borrowBook(selectedBook);
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

        if (!response.equals("[]")) {
            String[] books = response.split("\n"); // Split the titles by new line
            bookListView.getItems().addAll(books); // Add each title to the list view
        } else {
            showError("Failed to fetch books or no books available.");
        }
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
