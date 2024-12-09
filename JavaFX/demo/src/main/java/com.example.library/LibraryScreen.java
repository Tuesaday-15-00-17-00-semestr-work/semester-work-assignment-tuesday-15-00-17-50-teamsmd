package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibraryScreen {

    private MainApp mainApp;
    private BookService bookService = new BookService(); // Use BookService for API calls

    // Constructor
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
        Button addBookButton = new Button("Add New Book");

        // Fetch and display borrowed books
        fetchAvailableBooks(borrowedBooksListView);

        // Refresh action
        refreshButton.setOnAction(e -> fetchAvailableBooks(borrowedBooksListView));

        // Add new book action
        addBookButton.setOnAction(e -> openAddBookPopup(stage));

        layout.getChildren().addAll(titleLabel, borrowedBooksListView, refreshButton, addBookButton);
        mainApp.getBorderPane().setCenter(layout);
    }

    private void fetchAvailableBooks(ListView<String> listView) {
        String response = bookService.fetchAllBooks(); // Get the formatted titles
        listView.getItems().clear();
        if (!response.equals("[]")) {
            String[] books = response.split("\n"); // Split the titles by new line
            listView.getItems().addAll(books); // Add each title to the list view
        } else {
            showError("Failed to fetch borrowed books or no books available.");
        }
    }

    private void openAddBookPopup(Stage stage) {
        AddBookPopup addBookPopup = new AddBookPopup(mainApp);
        addBookPopup.showPopup(stage); // Open the popup
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
