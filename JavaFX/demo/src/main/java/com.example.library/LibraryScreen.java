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

    private MainApp mainApp; // Reference to MainApp

    // Constructor
    public LibraryScreen(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void start(Stage stage) {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER);

        // Title Label
        Label titleLabel = new Label("Your Borrowed Books");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // ListView to display borrowed books
        ListView<String> borrowedBooksListView = new ListView<>();

        // Get all transactions and filter the borrowed books
        for (Transaction transaction : mainApp.getTransactionManager().getTransactions()) {
            if ("Borrow".equals(transaction.getAction())) {
                borrowedBooksListView.getItems().add(transaction.getBookTitle());
            }
        }

        // Return Book button
        Button returnButton = new Button("Return Book");
        returnButton.setOnAction(e -> {
            String selectedBook = borrowedBooksListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                // Remove the return transaction from the list
                mainApp.getTransactionManager().addTransaction(selectedBook, "CurrentUser", "Return");
                borrowedBooksListView.getItems().remove(selectedBook);  // Remove it from the list
                showSuccess("You have returned \"" + selectedBook + "\"");
            } else {
                showError("Please select a book to return!");
            }
        });

        // Back Button to return to the Home screen
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> mainApp.loadHomeScreen(stage)); // Navigate back to Home screen

        layout.getChildren().addAll(titleLabel, borrowedBooksListView, returnButton, backButton);

        // Set the content in the main window by accessing `borderPane` from `mainApp`
        mainApp.getBorderPane().setCenter(layout);
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
