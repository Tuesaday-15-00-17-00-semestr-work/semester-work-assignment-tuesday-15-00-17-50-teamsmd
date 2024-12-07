package com.example.library;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddBookPopup {

    private MainApp mainApp;

    public AddBookPopup(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void showPopup(Stage ownerStage) {
        // Create the popup stage
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);  // Ensures that the popup is modal
        popupStage.initOwner(ownerStage);  // Makes sure that the main window stays in the background

        // Layout for the popup
        VBox popupLayout = new VBox(20);
        popupLayout.setStyle("-fx-padding: 20;");
        popupLayout.setAlignment(Pos.CENTER);

        Label popupLabel = new Label("Add New Book");
        popupLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Book Name Field
        Label nameLabel = new Label("Book Name:");
        TextField nameField = new TextField();
        nameField.setPrefWidth(200);

        // Author Field
        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField();
        authorField.setPrefWidth(200);

        // ISBN Field (new input)
        Label isbnLabel = new Label("ISBN:");
        TextField isbnField = new TextField();
        isbnField.setPrefWidth(200);

        // Available Copies Field (new input)
        Label availableCopiesLabel = new Label("Available Copies:");
        TextField availableCopiesField = new TextField();
        availableCopiesField.setPrefWidth(200);

        // Add Book button
        Button addButton = new Button("Add Book");
        addButton.setOnAction(e -> {
            String bookName = nameField.getText();
            String author = authorField.getText();
            int isbn = Integer.parseInt(isbnField.getText()); // Get ISBN value
            int availableCopies = Integer.parseInt(availableCopiesField.getText()); // Get available copies

            // Logic to add the book
            boolean success = mainApp.getBookService().addBook(bookName, author);

            if (success) {
                showSuccess("Book added successfully!");
                popupStage.close();  // Close the popup on success
            } else {
                showError("Failed to add book.");
            }
        });

        // Adding elements to the layout
        popupLayout.getChildren().addAll(popupLabel, nameLabel, nameField, authorLabel, authorField,
                isbnLabel, isbnField, availableCopiesLabel, availableCopiesField, addButton);

        // Set the scene for the popup
        Scene popupScene = new Scene(popupLayout, 300, 300);
        popupStage.setTitle("Add New Book");
        popupStage.setScene(popupScene);
        popupStage.show();  // Show the popup window
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
