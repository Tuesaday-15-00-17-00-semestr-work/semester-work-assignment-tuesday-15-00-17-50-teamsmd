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
        Label nameLabel = new Label("Book Title:");
        TextField titleField = new TextField();
        titleField.setPrefWidth(200);

        // Author Field
        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField();
        authorField.setPrefWidth(200);

        Button submitButton = new Button("Add Book");
        submitButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();

            // Check if fields are empty
            if (title.isEmpty() || author.isEmpty()) {
                showError("All fields must be filled!");
            } else {
                // Automatically set ISBN to 0 and available copies to 1
                boolean success = mainApp.getBookService().addBook(title, author);

                if (success) {
                    showSuccess("Book added successfully!");
                    popupStage.close();  // Close the popup on success
                } else {
                    showError("Failed to add book. Please try again.");
                }
            }
        });

        // Adding elements to the layout
        popupLayout.getChildren().addAll(popupLabel, nameLabel, titleField, authorLabel, authorField, submitButton);

        // Set the scene for the popup
        Scene popupScene = new Scene(popupLayout, 300, 250);
        popupStage.setTitle("Add New Book");
        popupStage.setScene(popupScene);
        popupStage.show();
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
