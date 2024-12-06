package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

        // Add New Book button
        Button addBookButton = new Button("Add New Book");
        addBookButton.setOnAction(e -> openAddBookPopup(stage));

        // You can add borrowedBooksListView and other controls here

        // Adding buttons to the layout
        layout.getChildren().addAll(titleLabel, addBookButton);

        // Set the content in the main window
        mainApp.getBorderPane().setCenter(layout);
    }

    // Function to open the popup for adding a new book
    private void openAddBookPopup(Stage stage) {
        // Create a new Stage for the Add Book popup
        AddBookPopup addBookPopup = new AddBookPopup(mainApp);  // Pass mainApp to handle book adding
        addBookPopup.showPopup(stage);  // Open the popup
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
