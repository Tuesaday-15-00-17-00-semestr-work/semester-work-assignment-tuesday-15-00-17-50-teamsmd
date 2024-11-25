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

    private MainApp mainApp; // Reference to MainApp

    // Constructor
    public BookLendingScreen(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void start(Stage stage) {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Available Books for Lending");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Sample list of available books (replace this with actual book data from your backend)
        ListView<String> bookListView = new ListView<>();
        bookListView.getItems().addAll("The Art of War", "Pride and Prejudice", "Moby Dick", "1984");

        // Borrow button
        Button borrowButton = new Button("Borrow");
        borrowButton.setOnAction(e -> {
            String selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                mainApp.getTransactionManager().addTransaction(selectedBook, "CurrentUser", "Borrow");
                showSuccess("You have borrowed \"" + selectedBook + "\"");
            } else {
                showError("Please select a book to borrow!");
            }
        });

        // Back Button to return to the Home screen
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> mainApp.loadHomeScreen(stage)); // Navigate back to Home screen

        layout.getChildren().addAll(titleLabel, bookListView, borrowButton, backButton);

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
