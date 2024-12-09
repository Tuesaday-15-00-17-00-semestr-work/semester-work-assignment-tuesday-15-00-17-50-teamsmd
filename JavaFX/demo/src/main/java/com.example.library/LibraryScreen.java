package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibraryScreen {

    private MainApp mainApp;
    private BookService bookService = new BookService(); // Use BookService for API calls

    public LibraryScreen(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void start(Stage stage) {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Your Borrowed Books");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ListView<String> borrowedBooksListView = new ListView<>();
        Button refreshButton = new Button("Refresh");
        Button returnButton = new Button("Return");

        // Initially, borrowed books list is empty
        refreshBooksList(borrowedBooksListView);

        // Refresh action
        refreshButton.setOnAction(e -> refreshBooksList(borrowedBooksListView));

        // Return action
        returnButton.setOnAction(e -> {
            String selectedBook = borrowedBooksListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                boolean success = bookService.returnBook(selectedBook); // Return book to library
                if (success) {
                    borrowedBooksListView.getItems().remove(selectedBook);
                    showSuccess("You have returned \"" + selectedBook + "\"");
                } else {
                    showError("Failed to return book. Please try again.");
                }
            } else {
                showError("Please select a book to return!");
            }
        });

        layout.getChildren().addAll(titleLabel, borrowedBooksListView, refreshButton, returnButton);
        mainApp.getBorderPane().setCenter(layout);
    }

    // Fetch borrowed books from backend
    private void refreshBooksList(ListView<String> borrowedBooksListView) {
        String response = bookService.fetchBorrowedBooks(); // Fetch borrowed books

        // Clear the list before adding new items
        borrowedBooksListView.getItems().clear();

        if (!response.equals("[]")) {
            String[] books = response.split("\n");
            borrowedBooksListView.getItems().addAll(books); // Add each borrowed book to the list
        } else {
            showError("No books borrowed yet.");
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
}
