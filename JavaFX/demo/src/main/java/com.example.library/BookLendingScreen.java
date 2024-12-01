package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;

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
        Button backButton = new Button("Back");

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

        // Back action
        backButton.setOnAction(e -> mainApp.loadHomeScreen(stage));

        layout.getChildren().addAll(titleLabel, bookListView, borrowButton, backButton);
        mainApp.getBorderPane().setCenter(layout);
    }

    private void fetchAvailableBooks(ListView<String> bookListView) {
        String response = bookService.fetchAllBooks();
        if (!response.equals("[]")) {
            String[] books = response.replace("[", "").replace("]", "").replace("\"", "").split(",");
            bookListView.getItems().addAll(Arrays.asList(books));
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
}
